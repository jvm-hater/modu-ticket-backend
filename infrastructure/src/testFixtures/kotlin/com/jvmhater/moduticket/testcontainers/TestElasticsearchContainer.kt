package com.jvmhater.moduticket.testcontainers

import org.testcontainers.elasticsearch.ElasticsearchContainer

class TestElasticsearchContainer {

    companion object {
        private const val DOCKER_IMAGE_NAME = "docker.elastic.co/elasticsearch/elasticsearch:8.6.1"
        private const val USERNAME = "elastic"
        private const val PASSWORD = "password"

        private lateinit var instance: ElasticsearchContainer

        fun start(): ElasticsearchProperty {
            if (!Companion::instance.isInitialized) {
                instance =
                    ElasticsearchContainer(DOCKER_IMAGE_NAME)
                        .withPassword(PASSWORD)
                        /*
                        disabled TLS and use plain text HTTP
                        https://spinscale.de/posts/2022-02-17-running-elasticsearch-8-with-testcontainers.html
                        */
                        .withEnv("xpack.security.enabled", "false")
                        .apply { start() }
            }

            return ElasticsearchProperty(
                httpHostAddress = instance.httpHostAddress,
                username = USERNAME,
                password = PASSWORD
            )
        }

        fun stop() {
            instance.stop()
        }
    }

    data class ElasticsearchProperty(
        val httpHostAddress: String,
        val username: String,
        val password: String
    )
}
