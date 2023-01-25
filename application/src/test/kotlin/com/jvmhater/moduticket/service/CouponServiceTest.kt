package com.jvmhater.moduticket.service

import com.jvmhater.moduticket.exception.RepositoryException
import com.jvmhater.moduticket.model.CouponFixture
import com.jvmhater.moduticket.repository.CouponRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.test.TestCase
import io.kotest.extensions.time.ConstantNowTestListener
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import java.time.LocalDateTime

class CouponServiceTest : DescribeSpec() {
    private lateinit var couponRepository: CouponRepository

    private lateinit var couponService: CouponService

    override fun listeners() =
        listOf(ConstantNowTestListener(LocalDateTime.of(2023, 1, 24, 10, 15, 30)))

    override suspend fun beforeEach(testCase: TestCase) {
        couponRepository = mockk()
        couponService = CouponService(couponRepository)
    }

    init {
        describe("#findCoupons") {
            context("특정 쿠폰 이름을 가진 Coupon Row가 있다면") {
                val name = "winter-event"
                val coupon = CouponFixture.generate(name = name)

                it("해당 쿠폰 리스트 조회에 성공한다.") {
                    coEvery { couponRepository.findCoupons(name) } returns listOf(coupon)

                    val foundCoupons = couponService.findCoupons(name)
                    foundCoupons shouldBe listOf(coupon)
                }
            }

            context("특정 쿠폰 이름을 가진 Coupon Row가 없다면") {
                val name = "not-found-name"

                it("빈 쿠폰 리스트를 조회한다.") {
                    coEvery { couponRepository.findCoupons(name) } returns emptyList()

                    couponService.findCoupons("not-found-name") shouldBe emptyList()
                }
            }
        }

        describe("#find") {
            context("특정 쿠폰 ID를 가진 Coupon Row가 있다면") {
                val coupon = CouponFixture.generate(id = "id")
                it("해당 쿠폰 조회에 성공한다.") {
                    coEvery { couponRepository.find(coupon.id) } returns coupon

                    val foundCoupon = couponService.find(coupon.id)
                    foundCoupon shouldBe coupon
                }
            }

            context("특정 쿠폰 ID를 가진 Coupon Row가 없다면") {
                val id = "not-found-id"

                it("쿠폰 조회에 실패한다.") {
                    coEvery { couponRepository.find(id) } throws
                        RepositoryException.RecordNotFound(message = "")

                    shouldThrow<RepositoryException.RecordNotFound> {
                        couponService.find("not-found-id")
                    }
                }
            }
        }

        describe("#save") {
            val coupon = CouponFixture.generate()
            context("새로운 Coupon 객체가 주어지면") {
                it("Coupon Row 삽입에 성공한다.") {
                    coEvery { couponRepository.create(coupon) } returns coupon

                    val savedCoupon = couponService.create(coupon)
                    coupon shouldBe savedCoupon
                }
            }

            context("이미 존재하는 Coupon 객체가 주어지면") {
                it("Coupon Row 삽입에 실패한다.") {
                    coEvery { couponRepository.create(any()) } throws
                        RepositoryException.RecordAlreadyExisted(message = "")

                    shouldThrow<RepositoryException.RecordAlreadyExisted> {
                        couponService.create(coupon)
                    }
                }
            }
        }

        describe("update") {
            context("특정 쿠폰 ID를 가진 Coupon 객체가 주어질 때") {
                val coupon = CouponFixture.generate()
                it("해당 쿠폰 ID 값인 Coupon Row 업데이트에 성공한다.") {
                    coEvery { couponRepository.update(coupon) } returns coupon
                    couponService.update(coupon) shouldBe coupon
                }

                it("해당 쿠폰 ID 값인 Coupon Row가 없다면 Coupon Row 업데이트에 실패한다.") {
                    coEvery { couponRepository.update(coupon) } throws
                        RepositoryException.RecordNotFound(message = "")

                    shouldThrow<RepositoryException.RecordNotFound> { couponService.update(coupon) }
                }
            }
        }

        describe("delete") {
            val coupon = CouponFixture.generate(id = "not-found-id")
            context("존재하지 않는 쿠폰 ID가 주어지면") {
                it("Coupon Row 삭제에 실패한다.") {
                    coEvery { couponRepository.delete(coupon.id) } throws
                        RepositoryException.RecordNotFound(message = "")

                    shouldThrow<RepositoryException.RecordNotFound> {
                        couponService.delete(coupon.id)
                    }
                }
            }
        }
    }
}
