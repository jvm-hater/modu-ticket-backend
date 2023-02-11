package com.jvmhater.moduticket.model

import com.jvmhater.moduticket.exception.DomainException
import com.jvmhater.moduticket.model.vo.Quantity
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.time.ConstantNowTestListener
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import java.time.LocalDateTime

class CouponTest : DescribeSpec() {

    private val fixedLocalDateTime = LocalDateTime.of(2023, 1, 24, 10, 15, 30)

    override fun listeners() = listOf(ConstantNowTestListener(fixedLocalDateTime))

    init {
        describe("#validateCouponUseDate") {
            context("발급 시작 기한이 현재보다 늦거나, 발급 마감 기한이 현재보다 빠르면") {
                it("예외를 던진다.") {
                    coEvery { LocalDateTime.now() } returns fixedLocalDateTime

                    val coupons =
                        listOf(
                            CouponFixture.generate(useStartDate = fixedLocalDateTime.plusDays(1L)),
                            CouponFixture.generate(useEndDate = fixedLocalDateTime.minusDays(1L))
                        )
                    coupons.forEach {
                        shouldThrow<DomainException.InvalidArgumentException> {
                            it.validateCouponUseDate()
                        }
                    }
                }
            }

            context("현재 시각이 발급 기한 안에 있으면") {
                it("예외가 발생하지 않는다.") {
                    coEvery { LocalDateTime.now() } returns fixedLocalDateTime

                    val coupon =
                        CouponFixture.generate(
                            useStartDate = fixedLocalDateTime.minusDays(1L),
                            useEndDate = fixedLocalDateTime.plusDays(1L)
                        )
                    shouldNotThrow<DomainException.InvalidArgumentException> {
                        coupon.validateCouponUseDate()
                    }
                }
            }
        }

        describe("issue") {
            context("발급 수량이 양수면") {
                val coupon = CouponFixture.generate(issuableQuantity = 1)
                it("발급 수량을 1개 줄인다.") {
                    coupon.issue() shouldBe coupon.copy(issuableQuantity = Quantity(0))
                }
            }

            context("발급 수량이 0보다 작거나 같으면") {
                val coupons =
                    listOf(
                        CouponFixture.generate(issuableQuantity = 0),
                        CouponFixture.generate(issuableQuantity = -1)
                    )
                it("예외를 던진다.") {
                    coupons.forEach {
                        shouldThrow<DomainException.InvalidArgumentException> { it.issue() }
                    }
                }
            }
        }
    }
}
