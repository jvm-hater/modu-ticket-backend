package com.jvmhater.moduticket.service

import co.elastic.clients.elasticsearch._types.SortOptions
import co.elastic.clients.elasticsearch._types.SortOrder
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery
import co.elastic.clients.elasticsearch._types.query_dsl.Query
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery
import co.elastic.clients.elasticsearch.core.SearchRequest
import com.jvmhater.moduticket.elasticsearch.ElasticsearchClient
import com.jvmhater.moduticket.elasticsearch.ElasticsearchProperty
import com.jvmhater.moduticket.elasticsearch.filterNullSkip
import com.jvmhater.moduticket.elasticsearch.index.ConcertFieldSet
import com.jvmhater.moduticket.elasticsearch.index.ConcertIndex
import com.jvmhater.moduticket.model.Concert
import com.jvmhater.moduticket.model.query.ConcertSearchQuery
import com.jvmhater.moduticket.model.query.Page
import com.jvmhater.moduticket.model.vo.ConcertCategory
import com.jvmhater.moduticket.util.then
import org.springframework.stereotype.Service

// TODO : 네이밍 추천 좀요.
@Service
class ElasticsearchConcertSearchService(
    private val client: ElasticsearchClient,
    private val property: ElasticsearchProperty
) : ConcertSearchService {
    override suspend fun search(query: ConcertSearchQuery, page: Page): List<Concert> {
        val boolQuery =
            BoolQuery.of { boolBuilder ->
                    boolBuilder.filterNullSkip(getCategoryTermQuery(query.category))
                    boolBuilder.must(getNameMatchQuery(query.searchText))
                }
                ._toQuery()

        val sortOptions =
            SortOptions.of { builder ->
                builder.field {
                    it.field(ConcertFieldSet.startDate.nestedName).order(SortOrder.Desc)
                }
            }

        val searchRequest =
            SearchRequest.of { builder ->
                builder.index(property.index.concert)
                builder.query(boolQuery)
                builder.sort(sortOptions)
                builder.from(page.page * page.size)
                builder.size(page.size)
            }

        return client.search(searchRequest, ConcertIndex::class.java).hits().hits().mapNotNull {
            it.source()?.toDomain()
        }
    }

    private fun getCategoryTermQuery(category: ConcertCategory): Query? {
        return (category != ConcertCategory.ALL).then {
            TermQuery.of { it.field(ConcertFieldSet.category.nestedName).value(category.name) }
                ._toQuery()
        }
    }

    private fun getNameMatchQuery(searchText: String): Query {
        return MatchQuery.of { it.field(ConcertFieldSet.name.nestedName).query(searchText) }
            ._toQuery()
    }
}
