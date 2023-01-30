package com.jvmhater.moduticket.model

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class CouponTest : DescribeSpec() {

    init {
        describe("#isIssueCoupon") {
            context("발급 수량이 양수면") {
                val coupon = CouponFixture.generate(issuableQuantity = 1)
                it("true를 반환한다.") {
                    coupon.isIssueCoupon() shouldBe true
                }
            }

            context("발급 수량이 0보다 작거나 같으면") {
                val coupons =
                    listOf(CouponFixture.generate(issuableQuantity = 0), CouponFixture.generate(issuableQuantity = -1))
                it("false를 반환한다.") {
                    coupons.forEach { it.isIssueCoupon() shouldBe false }
                }
            }
        }
    }
}
