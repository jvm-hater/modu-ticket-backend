package com.jvmhater.moduticket.model

import com.jvmhater.moduticket.util.generateId

object UserFixture {
    fun generate(
        id: String = generateId(),
        password: String = "testPassword",
        coupons: MutableList<Coupon> = mutableListOf(),
        point: Int = 0
    ): User {
        return User(id = id, password = password, coupons = coupons, point = point)
    }
}
