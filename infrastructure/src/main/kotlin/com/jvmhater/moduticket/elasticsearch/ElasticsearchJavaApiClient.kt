package com.jvmhater.moduticket.elasticsearch

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery
import co.elastic.clients.elasticsearch._types.query_dsl.Query
import co.elastic.clients.elasticsearch.core.SearchRequest
import co.elastic.clients.elasticsearch.core.SearchResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component

@Component
class ElasticsearchJavaApiClient(private val client: ElasticsearchAsyncClient) :
    ElasticsearchClient {
    override suspend fun <T> search(
        request: SearchRequest,
        documentClass: Class<T>
    ): SearchResponse<T> =
        withContext(Dispatchers.IO) { client.search(request, documentClass).get() }
}

fun BoolQuery.Builder.filterNullSkip(query: Query?) {
    query?.let { this.filter(it) }
}
