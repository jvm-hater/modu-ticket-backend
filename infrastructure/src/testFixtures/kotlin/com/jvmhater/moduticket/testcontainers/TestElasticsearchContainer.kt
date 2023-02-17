package com.jvmhater.moduticket.testcontainers

import org.testcontainers.elasticsearch.ElasticsearchContainer

class TestElasticsearchContainer {

    companion object {
        private const val DOCKER_IMAGE_NAME = "docker.elastic.co/elasticsearch/elasticsearch:8.6.1"

        private lateinit var instance: ElasticsearchContainer

        fun start(): ElasticsearchProperty {
            if (!Companion::instance.isInitialized) {
                instance = ElasticsearchContainer(DOCKER_IMAGE_NAME).apply { start() }
            }

            return ElasticsearchProperty(instance.httpHostAddress)
        }

        fun stop() {
            instance.stop()
        }
    }

    data class ElasticsearchProperty(val httpHostAddress: String)
}
