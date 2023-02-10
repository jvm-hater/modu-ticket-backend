package com.jvmhater.moduticket.service

import com.jvmhater.moduticket.exception.DomainException
import com.jvmhater.moduticket.exception.RepositoryException
import com.jvmhater.moduticket.model.CouponFixture
import com.jvmhater.moduticket.model.UserFixture
import com.jvmhater.moduticket.model.vo.Quantity
import com.jvmhater.moduticket.repository.CouponRepository
import com.jvmhater.moduticket.repository.UserRepository
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
    private lateinit var userRepository: UserRepository
    private lateinit var couponService: CouponService

    private val fixedLocalDateTime = LocalDateTime.of(2023, 1, 24, 10, 15, 30)

    override fun listeners() = listOf(ConstantNowTestListener(fixedLocalDateTime))

    override suspend fun beforeEach(testCase: TestCase) {
        couponRepository = mockk()
        userRepository = mockk()
        couponService = CouponService(couponRepository, userRepository)
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

        describe("#issue") {
            context("존재하지 않는 쿠폰 ID가 주어지면") {
                val coupon = CouponFixture.generate(id = "not-found-id")
                it("쿠폰을 발급할 수 없다.") {
                    coEvery { couponRepository.find(coupon.id) } throws
                        RepositoryException.RecordNotFound(message = "")

                    shouldThrow<RepositoryException.RecordNotFound> {
                        couponService.issueCoupon(userId = "", couponId = coupon.id)
                    }
                }
            }

            context("존재하는 쿠폰 ID가 주어지고") {
                val coupon =
                    CouponFixture.generate(
                        issuableQuantity = 1,
                        useStartDate = fixedLocalDateTime.minusDays(1L),
                        useEndDate = fixedLocalDateTime.plusDays(1L)
                    )

                beforeEach {
                    coEvery { couponRepository.find(coupon.id) } returns coupon
                    coEvery { LocalDateTime.now() } returns fixedLocalDateTime
                }

                context("유저가 이미 발급할 쿠폰을 보유하고 있으면") {
                    val user = UserFixture.generate(coupons = mutableListOf(coupon))
                    it("쿠폰을 발급할 수 없다.") {
                        coEvery { userRepository.findWithIssuedCoupon(user.id) } returns user

                        shouldThrow<DomainException.InvalidArgumentException> {
                            couponService.issueCoupon(userId = user.id, couponId = coupon.id)
                        }
                    }
                }

                context("유저가 발급할 쿠폰을 보유하고 있지 않으면") {
                    val user = UserFixture.generate()
                    val issuedCoupon =
                        coupon.copy(issuableQuantity = coupon.issuableQuantity - Quantity(1))
                    it("쿠폰을 발급한다.") {
                        coEvery { userRepository.findWithIssuedCoupon(user.id) } returns user
                        coEvery {
                            couponRepository.issue(userId = user.id, coupon = coupon)
                        } returns issuedCoupon

                        val actual =
                            couponService.issueCoupon(userId = user.id, couponId = coupon.id)
                        actual shouldBe issuedCoupon
                    }
                }
            }
        }
    }
}
