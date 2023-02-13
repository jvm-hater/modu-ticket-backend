package com.jvmhater.moduticket.model

import com.jvmhater.moduticket.exception.DomainException

data class User(
    val id: String,
    val password: String,
    val point: Int,
    // val reservationList: List<Reservation>?, TODO() 추후 추가 필요
    val coupons: MutableList<Coupon> = mutableListOf()
) {
    fun validateAlreadyIssueCoupon(coupon: Coupon) {
        if (coupons.contains(coupon)) {
            throw DomainException.InvalidArgumentException("이미 해당 쿠폰을 발급했습니다.")
        }
    }

    fun addCoupons(coupons: List<Coupon>) {
        this.coupons.addAll(coupons)
    }

    val rank: Rank by lazy { rateRank() }

    private fun rateRank(): Rank =
        when {
            point > 50000 -> {
                Rank.DIAMOND
            }
            point > 10000 -> {
                Rank.PLATINUM
            }
            point > 5000 -> {
                Rank.GOLD
            }
            point > 3000 -> {
                Rank.SILVER
            }
            else -> {
                Rank.BRONZE
            }
        }
}
