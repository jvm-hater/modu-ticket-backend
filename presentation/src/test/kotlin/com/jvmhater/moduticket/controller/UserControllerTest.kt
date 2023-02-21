package com.jvmhater.moduticket.controller

import com.jvmhater.moduticket.IntegrationTest
import com.jvmhater.moduticket.doDelete
import com.jvmhater.moduticket.doGet
import com.jvmhater.moduticket.doPost
import com.jvmhater.moduticket.dto.request.SignUpRequest
import com.jvmhater.moduticket.dto.response.UserResponse
import com.jvmhater.moduticket.kotest.CustomDescribeSpec
import com.jvmhater.moduticket.model.Rank
import com.jvmhater.moduticket.model.User
import com.jvmhater.moduticket.model.UserFixture
import com.jvmhater.moduticket.util.toJson
import org.springframework.test.web.reactive.server.WebTestClient

@IntegrationTest
class UserControllerTest(client: WebTestClient) :
    CustomDescribeSpec() {

    private val baseUrl = "/api/users"

    init {
        describe("#signUp") {
            context("새 유저 정보가 들어오면") {
                it("새 유저를 생성한다") {
                    client
                        .doPost(
                            "/api/signup",
                            request = SignUpRequest(id = "test", password = "password")
                        )
                        .expectStatus()
                        .isOk
                }
            }
            context("이미 존재하는 유저라면") {
                requestSignup(client, User(id = "test", password = "password", point = 0, coupons = mutableListOf()))
                it("유저 생성을 실패한다.") {
                    client
                        .doPost(
                            "/api/signup",
                            request = SignUpRequest(id = "test", password = "password")
                        )
                        .expectStatus()
                        .isBadRequest
                }
            }
        }
        describe("#find") {
            context("존재하는 유저의 정보가 들어오면") {
                requestSignup(client, User(id = "testId", password = "password", point = 0, coupons = mutableListOf()))
                it("해당 유저를 리턴한다") {
                    client
                        .doGet("$baseUrl/testId")
                        .expectStatus()
                        .isOk
                        .expectBody()
                        .json(
                            UserResponse(
                                id = "testId",
                                point = 0,
                                rank = Rank.BRONZE,
                                coupons = emptyList()
                            )
                                .toJson()
                        )
                }
            }
            context("존재하지 않는 유저라면") {
                it("조회를 실패한다.") { client.doGet("$baseUrl/test").expectStatus().isNotFound }
            }
        }

        describe("#delete") {
            context("존재하는 유저의 정보가 들어오면") {
                requestSignup(client, User(id = "testId", password = "password", point = 0, coupons = mutableListOf()))

                it("해당 유저를 삭제한다") {
                    client
                        .doDelete("$baseUrl/testId", mapOf("id" to "testId"))
                        .expectStatus()
                        .isNoContent
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

    companion object {
        const val BASE_URL = "/api/users"

        fun requestSignup(
            client: WebTestClient,
            user: User = UserFixture.generate(),
        ): UserResponse {
            client.doPost(
                "/api/signup",
                request = SignUpRequest(id = user.id, password = user.password)
            )

            return client
                .doGet("$BASE_URL/${user.id}")
                .expectBody(UserResponse::class.java)
                .returnResult()
                .responseBody!!
        }
    }
}
