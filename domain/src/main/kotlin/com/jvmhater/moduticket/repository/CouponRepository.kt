package com.jvmhater.moduticket.repository

import com.jvmhater.moduticket.model.Coupon

interface CouponRepository {

    suspend fun findCoupons(name: String): List<Coupon>

    suspend fun find(id: String): Coupon

    suspend fun create(coupon: Coupon): Coupon

    suspend fun update(coupon: Coupon): Coupon

    suspend fun delete(id: String)
}
