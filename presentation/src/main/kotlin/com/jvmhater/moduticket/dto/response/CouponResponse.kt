package com.jvmhater.moduticket.dto.response

import com.jvmhater.moduticket.model.ConcertCategory
import com.jvmhater.moduticket.model.Coupon
import java.time.LocalDateTime

class CouponResponse(
    val id: String,
    val name: String,
    val discountRate: Double,
    val concertCategories: List<ConcertCategory>,
    val maxDiscountAmount: Int,
    val useStartDate: LocalDateTime,
    val usedEndDate: LocalDateTime,
    val issuableQuantity: Int,
) {

    companion object {
        fun from(coupon: Coupon): CouponResponse {
            return coupon.run {
                CouponResponse(
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
    }
}