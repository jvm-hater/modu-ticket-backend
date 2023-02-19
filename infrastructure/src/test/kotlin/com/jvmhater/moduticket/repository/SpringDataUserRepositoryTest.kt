package com.jvmhater.moduticket.repository

import com.jvmhater.moduticket.TestContainerTest
import com.jvmhater.moduticket.exception.RepositoryException
import com.jvmhater.moduticket.kotest.CustomDescribeSpec
import com.jvmhater.moduticket.model.CouponFixture
import com.jvmhater.moduticket.model.IssuedCouponRow
import com.jvmhater.moduticket.model.UserFixture
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe

@TestContainerTest
class SpringDataUserRepositoryTest(
    r2dbcUserRepository: R2dbcUserRepository,
    r2dbcCouponRepository: R2dbcCouponRepository,
    private val r2dbcIssuedCouponRepository: R2dbcIssuedCouponRepository
) : CustomDescribeSpec() {

    private val userRepository =
        SpringDataUserRepository(
            r2dbcUserRepository,
            r2dbcCouponRepository,
            r2dbcIssuedCouponRepository
        )
    private val couponRepository =
        SpringDataCouponRepository(r2dbcCouponRepository, r2dbcIssuedCouponRepository)

    init {
        describe("#create") {
            val user = UserFixture.generate()
            context("새로운 id의 유저라면") {
                userRepository.create(userId = user.id, password = user.password)
                it("테이블에 값을 저장한다") { userRepository.find(user.id).id shouldBe user.id }
            }

            context("이미 있는 id의 유저라면") {
                userRepository.create(userId = user.id, password = user.password)
                it("값 저장을 실패한다.") {
                    shouldThrow<RepositoryException.RecordAlreadyExisted> {
                        userRepository.create(userId = user.id, password = user.password)
                    }
                }
            }
        }

        describe("#find") {
            context("테이블에 있는 유저라면") {
                val user = UserFixture.generate()
                userRepository.create(userId = user.id, password = user.password)
                it("해당 유저를 리턴한다.") { userRepository.find(user.id) shouldBe user }
            }

            context("해당 유저가 존재하지 않는다면") {
                it("조회를 실패한다.") {
                    shouldThrow<RepositoryException.RecordNotFound> {
                        userRepository.find(userId = "not-found-id")
                    }
                }
            }
        }

        describe("#findAll") {
            context("테이블에 있는 유저라면") {
                val user = UserFixture.generate()
                userRepository.create(userId = user.id, password = user.password)
                it("해당 유저를 삭제한다.") {
                    userRepository.delete(user.id) shouldBe Unit
                }
            }
            context("해당 유저가 존재하지 않는다면") {
                it("삭제를 실패한다.") {
                    shouldThrow<RepositoryException.RecordNotFound> {
                        userRepository.delete("not-found-id")
                    }
                }
            }
        }

        describe("#findWithIssuedCoupon") {
            context("테이블에 있는 유저라면") {
                val user = UserFixture.generate()
                userRepository.create(userId = user.id, password = user.password)

                val coupon = CouponFixture.generate()
                couponRepository.create(coupon)

                r2dbcIssuedCouponRepository.save(
                    IssuedCouponRow(isNewRow = true, userId = user.id, couponId = coupon.id)
                )

                it("발급된 쿠폰을 포함한 유저를 조회한다.") {
                    val expectedUser = user.copy(coupons = mutableListOf(coupon))

                    expectedUser shouldBe userRepository.findWithIssuedCoupon(user.id)
                }
            }

            context("테이블에 없는 유저라면") {
                it("쿠폰 발급에 실패한다.") {
                    shouldThrow<RepositoryException.RecordNotFound> {
                        userRepository.findWithIssuedCoupon("not-found-id")
                    }
                }
            }
        }
    }
}
