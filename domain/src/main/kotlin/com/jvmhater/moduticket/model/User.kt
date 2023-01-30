package com.jvmhater.moduticket.model

data class User(val coupons: List<Coupon> = ArrayList()) {
    fun hasCoupon(coupon: Coupon): Boolean {
        return coupons.contains(coupon)
    }
}
