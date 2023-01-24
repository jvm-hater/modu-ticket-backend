package com.jvmhater.moduticket.dto.request

import com.jvmhater.moduticket.model.ConcertCategory
import com.jvmhater.moduticket.model.Coupon
import java.time.LocalDateTime

class UpdateCouponRequest(
    val name: String,
    val discountRate: Int,
    val concertCategories: List<ConcertCategory>,
    val maxDiscountAmount: Int,
    val useStartDate: LocalDateTime,
    val useEndDate: LocalDateTime,
    val issuableQuantity: Int
) {

    fun toDomain(id: String): Coupon {
        return Coupon(
            id = id,
            name = name,
            discountRate = discountRate,
            concertCategory = concertCategories,
            maxDiscountAmount = maxDiscountAmount,
            useStartDate = useStartDate,
            useEndDate = useEndDate,
            issuableQuantity = issuableQuantity
        )
    }
}
