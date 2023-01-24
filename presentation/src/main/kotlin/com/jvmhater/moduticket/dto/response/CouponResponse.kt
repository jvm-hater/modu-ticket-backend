package com.jvmhater.moduticket.dto.response

import com.jvmhater.moduticket.model.ConcertCategory
import com.jvmhater.moduticket.model.Coupon
import java.time.LocalDateTime

class CouponResponse(
    val id: String,
    val name: String,
    val discountRate: Int,
    val concertCategory: ConcertCategory,
    val maxDiscountAmount: Int,
    val useStartDate: LocalDateTime,
    val useEndDate: LocalDateTime,
    val issuableQuantity: Int,
) {

    companion object {
        fun from(coupon: Coupon): CouponResponse {
            return coupon.run {
                CouponResponse(
                    id = id,
                    name = name,
                    discountRate = discountRate,
                    concertCategory = concertCategory,
                    maxDiscountAmount = maxDiscountAmount,
                    useStartDate = useStartDate,
                    useEndDate = useEndDate,
                    issuableQuantity = issuableQuantity
                )
            }
        }
    }
}
