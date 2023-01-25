package com.jvmhater.moduticket.controller

import com.jvmhater.moduticket.IntegrationTest
import io.kotest.core.spec.style.DescribeSpec
import org.springframework.test.web.reactive.server.WebTestClient

@IntegrationTest
class ModuControllerTest(client: WebTestClient) : DescribeSpec() {

    init {
        describe("#test") {
            it("success") {
                client
                    .get()
                    .uri("/api/test")
                    .exchange()
                    .expectStatus()
                    .isOk
                    .expectBody()
                    .consumeWith {}
            }
        }
    }
}
