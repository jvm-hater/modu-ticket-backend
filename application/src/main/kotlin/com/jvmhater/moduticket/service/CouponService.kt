package com.jvmhater.moduticket.service

import com.jvmhater.moduticket.exception.DomainException
import com.jvmhater.moduticket.model.Coupon
import com.jvmhater.moduticket.repository.CouponRepository
import com.jvmhater.moduticket.repository.IssuedCouponRepository
import com.jvmhater.moduticket.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CouponService(
    val couponRepository: CouponRepository,
    val userRepository: UserRepository,
    val issuedCouponRepository: IssuedCouponRepository,
) {

    @Transactional(readOnly = true)
    suspend fun findCoupons(name: String): List<Coupon> {
        return couponRepository.findCoupons(name)
    }

    @Transactional(readOnly = true)
    suspend fun find(id: String): Coupon {
        return couponRepository.find(id)
    }

    @Transactional
    suspend fun create(coupon: Coupon): Coupon {
        return couponRepository.create(coupon)
    }

    @Transactional
    suspend fun update(coupon: Coupon): Coupon {
        return couponRepository.update(coupon)
    }

    @Transactional
    suspend fun delete(id: String) {
        couponRepository.delete(id)
    }

    // TODO: 추후 동시성 검증을 거쳐야 합니다.
    @Transactional
    suspend fun issueCoupon(userId: String, couponId: String): Coupon {
        val coupon = couponRepository.find(couponId)

        if (coupon.isIssueCoupon()) {
            throw DomainException.InvalidArgumentException("해당 쿠폰 발급은 마감되었습니다.")
        }

        val user = userRepository.findWithIssuedCoupon(userId)

        if (user.hasCoupon(coupon)) {
            throw DomainException.InvalidArgumentException("이미 해당 쿠폰을 발급했습니다.")
        }

        val issuedCoupon = issuedCouponRepository.create(coupon, user)
        return issuedCoupon.coupon
    }
}
