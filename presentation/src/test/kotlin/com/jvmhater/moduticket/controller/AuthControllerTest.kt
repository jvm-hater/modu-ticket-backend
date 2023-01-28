package com.jvmhater.moduticket.controller

import com.jvmhater.moduticket.IntegrationTest
import com.jvmhater.moduticket.doPost
import com.jvmhater.moduticket.dto.request.AuthRequest
import com.jvmhater.moduticket.repository.UserRepository
import com.jvmhater.moduticket.testcontainers.TestMySQLContainer
import com.jvmhater.moduticket.util.readResourceFile
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import org.springframework.test.web.reactive.server.WebTestClient

@IntegrationTest
class AuthControllerTest(client: WebTestClient, val userRepository: UserRepository) :
    DescribeSpec() {

    override suspend fun afterEach(testCase: TestCase, result: TestResult) {
        TestMySQLContainer.sql(readResourceFile("ddl/truncate.sql"))
    }

    private val baseUrl = "/api/login"

    init {
        describe("#login") {
            context("when the user exists") {
                userRepository.create("testId", "testPassword")

                it("success") {
                    client
                        .doPost(baseUrl, AuthRequest(id = "testId", password = "testPassword"))
                        .expectStatus()
                        .isOk
                }
            }
            context("when the user not exists") {
                it("return not found status") {
                    client
                        .doPost(baseUrl, AuthRequest(id = "testId234", password = "testPassword"))
                        .expectStatus()
                        .isNotFound
                }
            }
        }
    }
}
