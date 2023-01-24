package com.jvmhater.moduticket.dto.request

import com.jvmhater.moduticket.model.ConcertCategory
import com.jvmhater.moduticket.model.Coupon
import java.time.LocalDateTime

class UpdateCouponRequest(
    val name: String,
    val discountRate: Double,
    val concertCategories: List<ConcertCategory>,
    val maxDiscountAmount: Int,
    val useStartDate: LocalDateTime,
    val usedEndDate: LocalDateTime,
    val issuableQuantity: Int
) {

    fun toDomain(id: String): Coupon {
        return Coupon(
            id = id,
            name = name,
            discountRate = discountRate,
            concertCategories = concertCategories,
            maxDiscountAmount = maxDiscountAmount,
            useStartDate = useStartDate,
            usedEndDate = usedEndDate,
            issuableQuantity = issuableQuantity
        )
    }
}