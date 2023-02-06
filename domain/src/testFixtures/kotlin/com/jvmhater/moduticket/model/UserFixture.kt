package com.jvmhater.moduticket.model

import com.jvmhater.moduticket.util.generateId

object UserFixture {
    fun generate(
        id: String = generateId(),
        password: String = "testPassword",
        coupons: List<Coupon> = mutableListOf(),
        rank: Rank = Rank.BRONZE,
        point: Int = 0
    ): User {
        return User(id = id, password = password, coupons = coupons, rank = rank, point = point)
    }
}
