package com.jvmhater.moduticket.model

import com.jvmhater.moduticket.util.generateId

data class IssuedCoupon(
    val id: String = generateId(ID_PREFIX),
    val coupon: Coupon,
    val user: User
) {
    companion object {
        const val ID_PREFIX = "issued-coupon-"
    }
}
