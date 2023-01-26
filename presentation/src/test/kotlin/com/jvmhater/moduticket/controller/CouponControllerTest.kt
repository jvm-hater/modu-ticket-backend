package com.jvmhater.moduticket.controller

import com.jvmhater.moduticket.IntegrationTest
import com.jvmhater.moduticket.dto.request.CreateCouponRequest
import com.jvmhater.moduticket.dto.request.UpdateCouponRequest
import com.jvmhater.moduticket.dto.response.CouponResponse
import com.jvmhater.moduticket.model.CouponFixture
import com.jvmhater.moduticket.repository.CouponRepository
import com.jvmhater.moduticket.testcontainers.TestMySQLContainer
import com.jvmhater.moduticket.util.readResourceFile
import com.jvmhater.moduticket.util.toJson
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.extensions.time.ConstantNowTestListener
import java.time.LocalDateTime
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters

@IntegrationTest
class CouponControllerTest(client: WebTestClient, val couponRepository: CouponRepository) :
    DescribeSpec() {
    override fun listeners() =
        listOf(ConstantNowTestListener(LocalDateTime.of(2023, 1, 24, 10, 15, 30)))

    override suspend fun afterEach(testCase: TestCase, result: TestResult) {
        TestMySQLContainer.sql(readResourceFile("ddl/truncate.sql"))
    }

    init {
        describe("#viewCoupons") {
            context("존재하는 쿠폰 이름이 주어지면") {
                val couponName = "winter-event"
                val coupon = couponRepository.create(CouponFixture.generate(name = couponName))

                it("해당 쿠폰 리스트를 조회한다.") {
                    val expectedResponse = listOf(CouponResponse.from(coupon))

                    client
                        .get()
                        .uri() { uriBuilder ->
                            uriBuilder
                                .path("/api/coupons")
                                .queryParam("coupon_name", couponName)
                                .build()
                        }
                        .exchange()
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
                        .get()
                        .uri() { uriBuilder ->
                            uriBuilder
                                .path("/api/coupons")
                                .queryParam("coupon_name", couponName)
                                .build()
                        }
                        .exchange()
                        .expectStatus()
                        .isOk
                        .expectBody()
                        .json(expectedResponse.toJson())
                }
            }
        }

        describe("#viewCoupon") {
            context("존재하는 쿠폰 ID가 주어지면") {
                val coupon = couponRepository.create(CouponFixture.generate())

                it("해당하는 쿠폰을 조회한다.") {
                    val expectedResponse = CouponResponse.from(coupon)

                    client
                        .get()
                        .uri("/api/coupons/{coupon_id}", coupon.id)
                        .exchange()
                        .expectStatus()
                        .isOk
                        .expectBody()
                        .json(expectedResponse.toJson())
                }
            }

            context("존재하지 않는 쿠폰 ID가 주어지면") {
                val couponId = "not-found-id"
                it("쿠폰을 조회할 수 없다.") {
                    client
                        .get()
                        .uri("/api/coupons/{coupon_id}", couponId)
                        .exchange()
                        .expectStatus()
                        .isNotFound
                }
            }
        }

        describe("#createCoupon") {
            context("신규 쿠폰이 주어지면") {
                val coupon = CouponFixture.generate()

                it("해당하는 쿠폰을 생성한다.") {
                    val createCouponRequest = CreateCouponRequest.from(coupon)

                    client
                        .post()
                        .uri("/api/coupons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(createCouponRequest))
                        .exchange()
                        .expectStatus()
                        .isCreated
                }
            }
        }

        describe("#updateCoupon") {
            context("쿠폰 ID가 존재하는 변경할 쿠폰이 주어지면") {
                val coupon = couponRepository.create(CouponFixture.generate())
                val couponToUpdate = coupon.copy(name = "update-name")

                it("해당하는 쿠폰이 변경된다.") {
                    val updateCouponRequest = UpdateCouponRequest.from(couponToUpdate)
                    val expectedCouponResponse = CouponResponse.from(couponToUpdate)

                    client
                        .put()
                        .uri("/api/coupons/{coupon_id}", couponToUpdate.id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(updateCouponRequest))
                        .exchange()
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
                        .put()
                        .uri("/api/coupons/{coupon_id}", couponToUpdate.id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(updateCouponRequest))
                        .exchange()
                        .expectStatus()
                        .isNotFound
                }
            }
        }

        describe("deleteCoupon") {
            context("존재하는 쿠폰 ID가 주어지면") {
                val coupon = couponRepository.create(CouponFixture.generate())

                it("해당하는 쿠폰이 제거된다.") {
                    client
                        .delete()
                        .uri("/api/coupons/{coupon_id}", coupon.id)
                        .exchange()
                        .expectStatus()
                        .isOk

                    client
                        .delete()
                        .uri("/api/coupons/{coupon_id}", coupon.id)
                        .exchange()
                        .expectStatus()
                        .isNotFound
                }
            }

            context("존재하지 않는 쿠폰 ID가 주어지면") {
                val couponId = "not-found-id"
                it("쿠폰을 제거할 수 없다.") {
                    client
                        .delete()
                        .uri("/api/coupons/{coupon_id}", couponId)
                        .exchange()
                        .expectStatus()
                        .isNotFound
                }
            }
        }
    }
}
