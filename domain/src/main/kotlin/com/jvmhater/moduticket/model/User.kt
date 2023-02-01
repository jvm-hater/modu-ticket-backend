package com.jvmhater.moduticket.model

import com.jvmhater.moduticket.util.generateId

data class User(
    val id: String = generateId(ID_PREFIX),
    val userId: String,
    val coupons: List<Coupon> = ArrayList()
) {
    companion object {
        const val ID_PREFIX = "user-"
    }

    fun hasCoupon(coupon: Coupon): Boolean {
        return coupons.contains(coupon)
    }
}
