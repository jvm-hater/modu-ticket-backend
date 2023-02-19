package com.jvmhater.moduticket.configuration

import co.elastic.clients.elasticsearch.ElasticsearchClient
import co.elastic.clients.json.jackson.JacksonJsonpMapper
import co.elastic.clients.transport.rest_client.RestClientTransport
import org.apache.http.HttpHost
import org.apache.http.auth.AuthScope
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.impl.client.BasicCredentialsProvider
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback
import org.elasticsearch.client.RestClientBuilder.RequestConfigCallback
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(ElasticsearchProperty::class)
class ElasticsearchConfiguration(private val elasticsearchProperty: ElasticsearchProperty) {

    @Bean
    fun httpClientConfigCallback(): HttpClientConfigCallback {
        val basicCredentialsProvider =
            BasicCredentialsProvider().apply {
                setCredentials(
                    AuthScope.ANY,
                    UsernamePasswordCredentials(
                        elasticsearchProperty.username,
                        elasticsearchProperty.password
                    )
                )
            }

        /*
        TODO : 인프라 구성 시 SSL, keepAliveStrategy, reuseStrategy 등 http 설정 필요
        */
        return HttpClientConfigCallback { builder ->
            builder.setDefaultCredentialsProvider(basicCredentialsProvider)
        }
    }

    @Bean
    fun requestConfigCallback(): RequestConfigCallback = RequestConfigCallback { builder ->
        builder
        /*
        TODO : http request 관련 설정 필요
        .setConnectTimeout(elasticsearchProperty.connectionTimeout)
        .setConnectionRequestTimeout(elasticsearchProperty.connectionRequestTimeout)
        */
    }

    @Bean
    fun elasticsearchClient(
        httpClientConfigCallback: HttpClientConfigCallback,
        requestConfigCallback: RequestConfigCallback
    ): ElasticsearchClient {
        val httpHost = HttpHost(elasticsearchProperty.host, elasticsearchProperty.port, "http")
        val restClient =
            RestClient.builder(httpHost)
                .setHttpClientConfigCallback(httpClientConfigCallback)
                .setRequestConfigCallback(requestConfigCallback)
                .build()
        val transport = RestClientTransport(restClient, JacksonJsonpMapper())

        return ElasticsearchClient(transport)
    }
}

@ConfigurationProperties(prefix = "elasticsearch")
data class ElasticsearchProperty(
    val host: String,
    val port: Int,
    val username: String,
    val password: String,
    val index: Index
) {
    data class Index(val concert: String)
}
