package com.jvmhater.moduticket.service

import com.jvmhater.moduticket.model.Coupon
import com.jvmhater.moduticket.repository.CouponRepository
import org.springframework.stereotype.Service

@Service
class CouponService(val couponRepository: CouponRepository) {

    suspend fun findCoupons(name: String): List<Coupon> {
        return couponRepository.findCoupons(name)
    }

    suspend fun find(id: String): Coupon {
        return couponRepository.find(id)
    }

    suspend fun create(coupon: Coupon): Coupon {
        return couponRepository.create(coupon)
    }

    suspend fun update(coupon: Coupon): Coupon {
        return couponRepository.update(coupon)
    }

    suspend fun delete(id: String) {
        couponRepository.delete(id)
    }
}
