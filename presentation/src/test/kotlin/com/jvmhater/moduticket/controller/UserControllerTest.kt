package com.jvmhater.moduticket.controller

import com.jvmhater.moduticket.IntegrationTest
import com.jvmhater.moduticket.doDelete
import com.jvmhater.moduticket.doGet
import com.jvmhater.moduticket.doPost
import com.jvmhater.moduticket.dto.request.AuthRequest
import com.jvmhater.moduticket.dto.response.UserResponse
import com.jvmhater.moduticket.model.Rank
import com.jvmhater.moduticket.repository.UserRepository
import com.jvmhater.moduticket.testcontainers.TestMySQLContainer
import com.jvmhater.moduticket.util.readResourceFile
import com.jvmhater.moduticket.util.toJson
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import org.springframework.test.web.reactive.server.WebTestClient

@IntegrationTest
class UserControllerTest(client: WebTestClient, val userRepository: UserRepository) :
    DescribeSpec() {

    override suspend fun afterEach(testCase: TestCase, result: TestResult) {
        TestMySQLContainer.sql(readResourceFile("ddl/truncate.sql"))
    }

    private val baseUrl = "/api/users"

    init {
        describe("#signUp") {
            context("새 유저 정보가 들어오면") {
                it("새 유저를 생성한다") {
                    client
                        .doPost(
                            "/api/signup",
                            request = AuthRequest(id = "test", password = "password")
                        )
                        .expectStatus()
                        .isOk
                }
            }
            context("이미 존재하는 유저라면") {
                userRepository.create("test", "password")
                it("유저 생성을 실패한다.") {
                    client
                        .doPost(
                            "/api/signup",
                            request = AuthRequest(id = "test", password = "password")
                        )
                        .expectStatus()
                        .isBadRequest
                }
            }
        }
        describe("#find") {
            context("존재하는 유저의 정보가 들어오면") {
                userRepository.create("testId", "password")
                it("해당 유저를 리턴한다") {
                    client
                        .doGet("$baseUrl/testId")
                        .expectStatus()
                        .isOk
                        .expectBody()
                        .json(UserResponse(id = "testId", point = 0, rank = Rank.BRONZE).toJson())
                }
            }
            context("존재하지 않는 유저라면") {
                it("조회를 실패한다.") { client.doGet("$baseUrl/test").expectStatus().isNotFound }
            }
        }

        describe("#delete") {
            context("존재하는 유저의 정보가 들어오면") {
                userRepository.create("testId", "password")
                it("해당 유저를 삭제한다") {
                    client.doDelete("$baseUrl/testId", mapOf("id" to "testId")).expectStatus().isOk
                }
            }
            context("존재하지 않는 유저라면") {
                it("삭제를 실패한다.") {
                    client
                        .doDelete("$baseUrl/test", mapOf("id" to "test"))
                        .expectStatus()
                        .isNotFound
                }
            }
        }
    }
}
