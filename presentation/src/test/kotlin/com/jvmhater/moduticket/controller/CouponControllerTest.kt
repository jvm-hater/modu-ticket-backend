package com.jvmhater.moduticket.controller

import com.jvmhater.moduticket.IntegrationTest
import com.jvmhater.moduticket.doDelete
import com.jvmhater.moduticket.doGet
import com.jvmhater.moduticket.doPost
import com.jvmhater.moduticket.doPut
import com.jvmhater.moduticket.dto.request.CreateCouponRequest
import com.jvmhater.moduticket.dto.request.IssueCouponRequest
import com.jvmhater.moduticket.dto.request.UpdateCouponRequest
import com.jvmhater.moduticket.dto.response.CouponResponse
import com.jvmhater.moduticket.dto.response.toResponse
import com.jvmhater.moduticket.model.Coupon
import com.jvmhater.moduticket.model.CouponFixture
import com.jvmhater.moduticket.testcontainers.TestMySQLContainer
import com.jvmhater.moduticket.util.readResourceFile
import com.jvmhater.moduticket.util.toJson
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.extensions.time.ConstantNowTestListener
import java.time.LocalDateTime
import org.springframework.test.web.reactive.server.WebTestClient

@IntegrationTest
class CouponControllerTest(client: WebTestClient) : DescribeSpec() {
    override fun listeners() =
        listOf(ConstantNowTestListener(LocalDateTime.of(2023, 1, 24, 10, 15, 30)))

    override suspend fun afterEach(testCase: TestCase, result: TestResult) {
        readResourceFile("ddl/truncate.sql").forEach { TestMySQLContainer.sql(it) }
    }

    init {
        describe("#viewCoupons") {
            context("존재하는 쿠폰 이름이 주어지면") {
                val couponName = "winter-event"
                val couponResponse =
                    requestCreateCoupon(client, CouponFixture.generate(name = couponName))

                it("해당 쿠폰 리스트를 조회한다.") {
                    val expectedResponse = listOf(couponResponse)

                    client
                        .doGet(BASE_URL, mapOf(Pair("coupon_name", couponName)))
                        .expectStatus()
                        .isOk
                        .expectBody()
                        .json(expectedResponse.toJson())
                }
            }

            context("존재하지 않는 쿠폰 이름이 주어지면") {
                val couponName = "not-found-coupon-name"

                it("빈 쿠폰 리스트를 조회한다.") {
                    val expectedResponse = emptyList<CouponResponse>()

                    client
                        .doGet(url = BASE_URL, queryParams = mapOf(Pair("coupon_name", couponName)))
                        .expectStatus()
                        .isOk
                        .expectBody()
                        .json(expectedResponse.toJson())
                }
            }
        }

        describe("#viewCoupon") {
            context("존재하는 쿠폰 ID가 주어지면") {
                val couponResponse = requestCreateCoupon(client)

                it("해당하는 쿠폰을 조회한다.") {
                    client
                        .doGet("$BASE_URL/${couponResponse.id}")
                        .expectStatus()
                        .isOk
                        .expectBody()
                        .json(couponResponse.toJson())
                }
            }

            context("존재하지 않는 쿠폰 ID가 주어지면") {
                val couponId = "not-found-id"
                it("쿠폰을 조회할 수 없다.") {
                    client.doGet("$BASE_URL/$couponId").expectStatus().isNotFound
                }
            }
        }

        describe("#createCoupon") {
            context("신규 쿠폰이 주어지면") {
                val coupon = CouponFixture.generate()

                it("해당하는 쿠폰을 생성한다.") {
                    val createCouponRequest = CreateCouponRequest.from(coupon)

                    client
                        .doPost(url = BASE_URL, request = createCouponRequest)
                        .expectStatus()
                        .isCreated
                }
            }
        }

        describe("#updateCoupon") {
            context("쿠폰 ID가 존재하는 변경할 쿠폰이 주어지면") {
                val couponResponse = requestCreateCoupon(client)
                val couponToUpdate = couponResponse.toDomain().copy(name = "update-name")

                it("해당하는 쿠폰이 변경된다.") {
                    val updateCouponRequest = UpdateCouponRequest.from(couponToUpdate)
                    val expectedCouponResponse = couponToUpdate.toResponse()

                    client
                        .doPut(
                            url = "$BASE_URL/${couponToUpdate.id}",
                            request = updateCouponRequest
                        )
                        .expectStatus()
                        .isOk
                        .expectBody()
                        .json(expectedCouponResponse.toJson())
                }
            }

            context("쿠폰 ID가 존재하지 않는 변경할 쿠폰이 주어지면") {
                val couponToUpdate = CouponFixture.generate()

                it("쿠폰을 변경할 수 없다.") {
                    val updateCouponRequest = UpdateCouponRequest.from(couponToUpdate)

                    client
                        .doPut(
                            url = "$BASE_URL/${couponToUpdate.id}",
                            request = updateCouponRequest
                        )
                        .expectStatus()
                        .isNotFound
                }
            }
        }

        describe("deleteCoupon") {
            context("존재하는 쿠폰 ID가 주어지면") {
                val couponResponse = requestCreateCoupon(client)

                it("해당하는 쿠폰이 제거된다.") {
                    client.doDelete("$BASE_URL/${couponResponse.id}").expectStatus().isNoContent
                    client.doDelete("$BASE_URL/${couponResponse.id}").expectStatus().isNotFound
                }
            }

            context("존재하지 않는 쿠폰 ID가 주어지면") {
                val couponId = "not-found-id"
                it("쿠폰을 제거할 수 없다.") {
                    client.doDelete("$BASE_URL/$couponId").expectStatus().isNotFound
                }
            }
        }

        describe("#issueCoupon") {
            context("존재하는 유저 ID, 쿠폰 ID가 주어지면") {
                val userResponse = UserControllerTest.requestSignup(client)
                val coupon = requestCreateCoupon(client).toDomain()
                val issueCouponRequest =
                    IssueCouponRequest(userId = userResponse.id, couponId = coupon.id)

                it("쿠폰 발급에 성공한다.") {
                    val expectedCouponResponse =
                        coupon.copy(issuableQuantity = coupon.issuableQuantity - 1).toResponse()
                    client
                        .doPost("$BASE_URL/issue-coupon", issueCouponRequest)
                        .expectStatus()
                        .isCreated
                        .expectBody()
                        .json(expectedCouponResponse.toJson())
                }
            }
        }
    }

    companion object {
        const val BASE_URL = "/api/coupons"

        fun requestCreateCoupon(
            client: WebTestClient,
            coupon: Coupon = CouponFixture.generate(),
        ): CouponResponse {
            val createCouponRequest = CreateCouponRequest.from(coupon)

            return client
                .doPost(url = BASE_URL, request = createCouponRequest)
                .expectBody(CouponResponse::class.java)
                .returnResult()
                .responseBody!!
        }
    }
}
