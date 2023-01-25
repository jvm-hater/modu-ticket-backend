package com.jvmhater.moduticket.controller

import com.jvmhater.moduticket.IntegrationTest
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
import org.springframework.test.web.reactive.server.WebTestClient

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
            context("쿠폰 이름이 주어지면") {
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
        }
    }
}
