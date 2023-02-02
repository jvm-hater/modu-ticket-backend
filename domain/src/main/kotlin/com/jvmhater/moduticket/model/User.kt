package com.jvmhater.moduticket.model

data class User(
    val id: String,
    val password: String,
    val point: Int,
    val rank: Rank,
    // val reservationList: List<Reservation>?, TODO() 추후 추가 필요
    val coupons: List<Coupon> = ArrayList()
)
