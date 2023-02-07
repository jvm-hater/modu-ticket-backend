package com.jvmhater.moduticket.model

import com.jvmhater.moduticket.exception.DomainException

data class User(
    val id: String,
    val password: String,
    val point: Int,
    val rank: Rank,
    // val reservationList: List<Reservation>?, TODO() 추후 추가 필요
    val coupons: List<Coupon> = emptyList()
) {
    fun validateAlreadyIssueCoupon(coupon: Coupon) {
        if (coupons.contains(coupon)) {
            throw DomainException.InvalidArgumentException("이미 해당 쿠폰을 발급했습니다.")
        }
    }

    fun updateCoupons(coupons: List<Coupon>): User {
        return this.copy(coupons = coupons)
    }
}
