package com.jvmhater.moduticket.dto.response

import com.jvmhater.moduticket.model.Rank
import com.jvmhater.moduticket.model.User

data class UserResponse(
    val id: String,
    val point: Int,
    val rank: Rank,
    val coupons: List<CouponResponse>
) {
    companion object {
        fun from(user: User): UserResponse {
            return UserResponse(
                id = user.id,
                point = user.point,
                rank = user.rank,
                coupons = user.coupons.toResponses()
            )
        }
    }
}
