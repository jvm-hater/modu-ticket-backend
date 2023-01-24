package com.jvmhater.moduticket.repository

import com.jvmhater.moduticket.model.CouponFixture
import com.jvmhater.moduticket.readResourceFile
import com.jvmhater.moduticket.testcontainers.TestMySQLContainer
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.Clock
import java.time.Instant
import java.time.ZoneId
import org.springframework.test.context.ContextConfiguration
import useFixedClock

@ContextConfiguration(classes = [TestR2bcConfiguration::class])
class SpringDataCouponRepositoryTest(
    r2dbcCouponRepository: R2dbcCouponRepository,
) :
    DescribeSpec({
        beforeEach {
            useFixedClock(Clock.fixed(Instant.parse("2023-01-24T10:15:30.00Z"), ZoneId.of("UTC")))
            TestMySQLContainer.sql(readResourceFile("ddl/truncate.sql"))
        }
    }) {

    private val couponRepository = SpringDataCouponRepository(r2dbcCouponRepository)

    init {
        describe("#save") {
            context("유효한 Coupon 객체가 주어지면") {
                it("Coupon Row를 삽입에 성공한다.") {
                    val coupon = CouponFixture.generate()
                    val savedCoupon = couponRepository.create(coupon)
                    savedCoupon shouldBe couponRepository.find(savedCoupon.id)
                }
            }

            context("이미 존재하는 Coupon 객체가 주어지면") {
                val coupon = couponRepository.create(CouponFixture.generate())
                it("Coupon Row 삽입에 실패한다.") { couponRepository.create(coupon) }
            }
        }
    }
}
