package com.jvmhater.moduticket.model

import com.jvmhater.moduticket.util.generateId

object UserFixture {
    fun generate(
        id: String = generateId(User.ID_PREFIX),
        userId: String = "user-id",
        coupons: List<Coupon> = mutableListOf()
    ): User {
        return User(
            id = id,
            userId = userId,
            coupons = coupons
        )
    }
}