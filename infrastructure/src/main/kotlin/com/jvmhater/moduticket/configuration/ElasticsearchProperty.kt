package com.jvmhater.moduticket.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

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
