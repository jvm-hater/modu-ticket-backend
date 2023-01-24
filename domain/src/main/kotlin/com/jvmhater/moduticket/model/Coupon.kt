package com.jvmhater.moduticket.model

import java.time.LocalDateTime
import java.util.UUID

data class Coupon(
    val id: String = generateId(),
    val name: String,
    val discountRate: Double,
    val concertCategories: List<ConcertCategory>,
    val maxDiscountAmount: Int,
    val useStartDate: LocalDateTime,
    val usedEndDate: LocalDateTime,
    val issuableQuantity: Int,
) {
    companion object {
        private const val ID_PREFIX = "coupon-id-"

        fun generateId(): String = ID_PREFIX + UUID.randomUUID().toString()
    }
}