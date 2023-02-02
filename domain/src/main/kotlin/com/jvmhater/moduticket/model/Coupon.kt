package com.jvmhater.moduticket.model

import com.jvmhater.moduticket.util.generateId
import java.time.LocalDateTime

data class Coupon(
    val id: String = generateId(ID_PREFIX),
    val name: String,
    val discountRate: Int,
    val concertCategory: ConcertCategory,
    val maxDiscountAmount: Int,
    val useStartDate: LocalDateTime,
    val useEndDate: LocalDateTime,
    val issuableQuantity: Int,
) {
    companion object {
        const val ID_PREFIX = "coupon-id-"
    }
}
