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
import com.jvmhater.moduticket.model.CouponFixture
import com.jvmhater.moduticket.model.UserFixture
import com.jvmhater.moduticket.repository.CouponRepository
import com.jvmhater.moduticket.repository.UserRepository
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
class CouponControllerTest(
    client: WebTestClient,
    val couponRepository: CouponRepository,
    val userRepository: UserRepository
) : DescribeSpec() {
    override fun listeners() =
        listOf(ConstantNowTestListener(LocalDateTime.of(2023, 1, 24, 10, 15, 30)))

    override suspend fun afterEach(testCase: TestCase, result: TestResult) {
        TestMySQLContainer.sql(readResourceFile("ddl/truncate.sql"))
    }

    private val baseUrl = "/api/coupons"

    init {
        describe("#viewCoupons") {
            context("존재하는 쿠폰 이름이 주어지면") {
                val couponName = "winter-event"
                val coupon = couponRepository.create(CouponFixture.generate(name = couponName))

                it("해당 쿠폰 리스트를 조회한다.") {
                    val expectedResponse = listOf(CouponResponse.from(coupon))

                    client
                        .doGet(baseUrl, mapOf(Pair("coupon_name", couponName)))
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
                        .doGet(url = baseUrl, queryParams = mapOf(Pair("coupon_name", couponName)))
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
                        .doGet("$baseUrl/${coupon.id}")
                        .expectStatus()
                        .isOk
                        .expectBody()
                        .json(expectedResponse.toJson())
                }
            }

            context("존재하지 않는 쿠폰 ID가 주어지면") {
                val couponId = "not-found-id"
                it("쿠폰을 조회할 수 없다.") { client.doGet("$baseUrl/$couponId").expectStatus().isNotFound }
            }
        }

        describe("#createCoupon") {
            context("신규 쿠폰이 주어지면") {
                val coupon = CouponFixture.generate()

                it("해당하는 쿠폰을 생성한다.") {
                    val createCouponRequest = CreateCouponRequest.from(coupon)

                    client
                        .doPost(url = baseUrl, request = createCouponRequest)
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
                        .doPut(url = "$baseUrl/${couponToUpdate.id}", request = updateCouponRequest)
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
                        .doPut(url = "$baseUrl/${couponToUpdate.id}", request = updateCouponRequest)
                        .expectStatus()
                        .isNotFound
                }
            }
        }

        describe("deleteCoupon") {
            context("존재하는 쿠폰 ID가 주어지면") {
                val coupon = couponRepository.create(CouponFixture.generate())

                it("해당하는 쿠폰이 제거된다.") {
                    client.doDelete("$baseUrl/${coupon.id}").expectStatus().isNoContent
                    client.doDelete("$baseUrl/${coupon.id}").expectStatus().isNotFound
                }
            }

            context("존재하지 않는 쿠폰 ID가 주어지면") {
                val couponId = "not-found-id"
                it("쿠폰을 제거할 수 없다.") {
                    client.doDelete("$baseUrl/$couponId").expectStatus().isNotFound
                }
            }
        }

        describe("#issueCoupon") {
            context("존재하는 유저 ID, 쿠폰 ID가 주어지면") {
                val user = UserFixture.generate()
                userRepository.create(id = user.id, password = user.password)

                val coupon = CouponFixture.generate()
                couponRepository.create(coupon)

                val issueCouponRequest = IssueCouponRequest(userId = user.id, couponId = coupon.id)

                it("쿠폰 발급에 성공한다.") {
                    val expectedCouponResponse =
                        CouponResponse.from(
                            coupon.copy(issuableQuantity = coupon.issuableQuantity - 1)
                        )
                    client
                        .doPost("$baseUrl/issue-coupon", issueCouponRequest)
                        .expectStatus()
                        .isCreated
                        .expectBody()
                        .json(expectedCouponResponse.toJson())
                }
            }
        }
    }
}
