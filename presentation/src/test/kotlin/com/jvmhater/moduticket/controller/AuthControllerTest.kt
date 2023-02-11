package com.jvmhater.moduticket.controller

import com.jvmhater.moduticket.IntegrationTest
import com.jvmhater.moduticket.doPost
import com.jvmhater.moduticket.dto.request.LoginRequest
import com.jvmhater.moduticket.kotest.CustomDescribeSpec
import com.jvmhater.moduticket.repository.UserRepository
import org.springframework.test.web.reactive.server.WebTestClient

@IntegrationTest
class AuthControllerTest(client: WebTestClient, val userRepository: UserRepository) :
    CustomDescribeSpec() {

    private val baseUrl = "/api/login"

    init {
        describe("#login") {
            context("존재하는 유저의 정보가 입력되면") {
                userRepository.create("testId", "testPassword")

                it("로그인에 성공한다.") {
                    client
                        .doPost(baseUrl, LoginRequest(id = "testId", password = "testPassword"))
                        .expectStatus()
                        .isOk
                }
            }
            context("해당 유저가 존재하지 않는다면") {
                it("로그인에 실패한다.") {
                    client
                        .doPost(baseUrl, LoginRequest(id = "testId234", password = "testPassword"))
                        .expectStatus()
                        .isNotFound
                }
            }
        }
    }
}
