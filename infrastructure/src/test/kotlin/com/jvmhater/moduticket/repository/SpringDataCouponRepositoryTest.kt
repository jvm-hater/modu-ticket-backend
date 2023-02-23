package com.jvmhater.moduticket.repository

import com.jvmhater.moduticket.TestMySQLContainerTest
import com.jvmhater.moduticket.exception.RepositoryException
import com.jvmhater.moduticket.kotest.CustomDescribeSpec
import com.jvmhater.moduticket.model.CouponFixture
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe

@TestMySQLContainerTest
class SpringDataCouponRepositoryTest(
    r2dbcCouponRepository: R2dbcCouponRepository,
    r2dbcIssuedCouponRepository: R2dbcIssuedCouponRepository
) : CustomDescribeSpec() {

    private val couponRepository =
        SpringDataCouponRepository(r2dbcCouponRepository, r2dbcIssuedCouponRepository)

    init {
        describe("#findCoupons") {
            context("특정 쿠폰 이름을 가진 Coupon Row가 있다면") {
                val name = "winter-event"
                val coupon = couponRepository.create(CouponFixture.generate(name = name))
                it("해당 쿠폰 리스트 조회에 성공한다.") {
                    val foundCoupons = couponRepository.findCoupons(name)
                    foundCoupons shouldBe listOf(coupon)
                }
            }

            context("특정 쿠폰 이름을 가진 Coupon Row가 없다면") {
                it("빈 쿠폰 리스트를 조회한다.") {
                    couponRepository.findCoupons("not-found-name") shouldBe emptyList()
                }
            }
        }

        describe("#find") {
            context("특정 쿠폰 ID를 가진 Coupon Row가 있다면") {
                val coupon = couponRepository.create(CouponFixture.generate())
                it("해당 쿠폰 조회에 성공한다.") {
                    val foundCoupon = couponRepository.find(coupon.id)
                    foundCoupon shouldBe coupon
                }
            }

            context("특정 쿠폰 ID를 가진 Coupon Row가 없다면") {
                it("쿠폰 조회에 실패한다.") {
                    shouldThrow<RepositoryException.RecordNotFound> {
                        couponRepository.find("not-found-id")
                    }
                }
            }
        }

        describe("#save") {
            context("새로운 Coupon 객체가 주어지면") {
                it("Coupon Row 삽입에 성공한다.") {
                    val coupon = CouponFixture.generate()
                    val savedCoupon = couponRepository.create(coupon)
                    coupon shouldBe savedCoupon
                }
            }

            context("이미 존재하는 Coupon 객체가 주어지면") {
                val coupon = couponRepository.create(CouponFixture.generate())
                it("Coupon Row 삽입에 실패한다.") {
                    shouldThrow<RepositoryException.RecordAlreadyExisted> {
                        couponRepository.create(coupon)
                    }
                }
            }
        }

        describe("update") {
            context("특정 쿠폰 ID를 가진 Coupon 객체가 주어질 때") {
                it("해당 쿠폰 ID 값인 Coupon Row 업데이트에 성공한다.") {
                    val coupon = couponRepository.create(CouponFixture.generate())
                    val updateCoupon = CouponFixture.generate(id = coupon.id, name = "update-name")
                    couponRepository.update(updateCoupon) shouldBe updateCoupon
                }

                it("해당 쿠폰 ID 값인 Coupon Row가 없다면 Coupon Row 업데이트에 실패한다.") {
                    shouldThrow<RepositoryException.RecordNotFound> {
                        couponRepository.update(CouponFixture.generate())
                    }
                }
            }
        }

        describe("delete") {
            context("존재하는 쿠폰 ID가 주어지면") {
                val coupon = couponRepository.create(CouponFixture.generate())
                it("해당 쿠폰 ID 값인 Coupon Row 삭제에 성공한다.") {
                    couponRepository.delete(coupon.id)

                    shouldThrow<RepositoryException.RecordNotFound> {
                        couponRepository.find(coupon.id)
                    }
                }
            }

            context("존재하지 않는 쿠폰 ID가 주어지면") {
                it("Coupon Row 삭제에 실패한다.") {
                    shouldThrow<RepositoryException.RecordNotFound> {
                        couponRepository.delete("not-found-id")
                    }
                }
            }
        }
    }
}
