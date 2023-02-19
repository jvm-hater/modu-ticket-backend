package com.jvmhater.moduticket.configuration

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient
import co.elastic.clients.json.jackson.JacksonJsonpMapper
import co.elastic.clients.transport.rest_client.RestClientTransport
import com.jvmhater.moduticket.testcontainers.TestElasticsearchContainer
import org.apache.http.HttpHost
import org.apache.http.auth.AuthScope
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.impl.client.BasicCredentialsProvider
import org.elasticsearch.client.RestClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TestElasticsearchConfiguration {

    @Bean
    fun elasticsearchAsyncClient(): ElasticsearchAsyncClient {
        val property = TestElasticsearchContainer.start()

        val httpHost = HttpHost.create(property.httpHostAddress)
        val restClient =
            RestClient.builder(httpHost)
                .setHttpClientConfigCallback { builder ->
                    builder.setDefaultCredentialsProvider(
                        BasicCredentialsProvider().apply {
                            setCredentials(
                                AuthScope.ANY,
                                UsernamePasswordCredentials(property.username, property.password)
                            )
                        }
                    )
                }
                .build()
        val transport = RestClientTransport(restClient, JacksonJsonpMapper())

        return ElasticsearchAsyncClient(transport)
    }
}
