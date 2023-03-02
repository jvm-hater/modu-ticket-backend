package com.jvmhater.moduticket

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest
import co.elastic.clients.elasticsearch.indices.ExistsRequest
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.springframework.core.io.ClassPathResource

// TODO : elasticsearch test 예제이므로 삭제해야 합니다.
@TestElasticsearchContainerTest
class ElasticsearchConnectionTest(private val client: ElasticsearchAsyncClient) : DescribeSpec() {

    init {
        coroutineTestScope = true

        describe("#connect") {
            context("elasticsearch") {
                val result =
                    client
                        .indices()
                        .exists(ExistsRequest.of { builder -> builder.index("index") })
                        .get()
                        .value()

                result shouldBe false
            }
        }

        describe("#elasticsearch") {
            context("search") {
                val json = ClassPathResource("/elasticsearch/concert.json5").inputStream
                println("hey json : $json")
                val request = CreateIndexRequest.Builder().index("concerts").withJson(json).build()
                client.indices().create(request).get()
            }
        }
    }
}
