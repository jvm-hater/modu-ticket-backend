package com.jvmhater.moduticket.model

import com.jvmhater.moduticket.util.generateId

object UserFixture {
    fun generate(
        id: String = generateId(),
        coupons: List<Coupon> = emptyList(),
        rank: Rank = Rank.DIAMOND,
        point: Int = 1000
    ): User {
        return User(id = id, coupons = coupons, rank = rank, point = point)
    }
}
