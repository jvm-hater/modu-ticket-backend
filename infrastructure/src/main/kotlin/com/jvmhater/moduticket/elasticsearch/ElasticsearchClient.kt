package com.jvmhater.moduticket.elasticsearch

import co.elastic.clients.elasticsearch.core.SearchRequest
import co.elastic.clients.elasticsearch.core.SearchResponse

interface ElasticsearchClient {
    suspend fun <T> search(request: SearchRequest, documentClass: Class<T>): SearchResponse<T>
}
