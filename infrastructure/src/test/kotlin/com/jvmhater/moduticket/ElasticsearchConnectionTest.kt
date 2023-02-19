package com.jvmhater.moduticket

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient
import co.elastic.clients.elasticsearch.indices.ExistsRequest
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

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
    }
}
