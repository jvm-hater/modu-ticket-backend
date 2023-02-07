package com.jvmhater.moduticket.model

import com.jvmhater.moduticket.exception.DomainException
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class UserTest : DescribeSpec() {

    init {
        describe("#validateAlreadyIssueCoupon") {
            context("해당 쿠폰을 발급한 적이 없으면") {
                val coupon = CouponFixture.generate()
                val user = UserFixture.generate()

                it("예외가 발생하지 않는다.") {
                    shouldNotThrow<DomainException.InvalidArgumentException> {
                        user.validateAlreadyIssueCoupon(coupon)
                    }
                }
            }

            context("해당 쿠폰을 이미 발급했으면") {
                val coupon = CouponFixture.generate()
                val user = UserFixture.generate(coupons = listOf(coupon))

                it("예외가 발생한다.") {
                    shouldThrow<DomainException.InvalidArgumentException> {
                        user.validateAlreadyIssueCoupon(coupon)
                    }
                }
            }
        }

        describe("#updateCoupons") {
            it("주어진 쿠폰 리스트로 업데이트한다.") {
                val coupon = CouponFixture.generate()
                val user = UserFixture.generate()

                user.updateCoupons(coupons = listOf(coupon)) shouldBe
                    user.copy(coupons = listOf(coupon))
            }
        }
    }
}
