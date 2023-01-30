package com.jvmhater.moduticket.repository

import com.jvmhater.moduticket.model.Coupon
import com.jvmhater.moduticket.model.IssuedCoupon
import com.jvmhater.moduticket.model.User

interface IssuedCouponRepository {

    suspend fun create(coupon: Coupon, user: User): IssuedCoupon
}
