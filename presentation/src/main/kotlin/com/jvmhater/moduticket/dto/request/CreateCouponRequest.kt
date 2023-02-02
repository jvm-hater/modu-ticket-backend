package com.jvmhater.moduticket.dto.request

import com.jvmhater.moduticket.model.Coupon
import com.jvmhater.moduticket.model.vo.ConcertCategory
import java.time.LocalDateTime

class CreateCouponRequest(
    val name: String,
    val discountRate: Int,
    val concertCategory: ConcertCategory,
    val maxDiscountAmount: Int,
    val useStartDate: LocalDateTime,
    val useEndDate: LocalDateTime,
    val issuableQuantity: Int,
) {

    fun toDomain(): Coupon {
        return Coupon(
            name = name,
            discountRate = discountRate,
            concertCategory = concertCategory,
            maxDiscountAmount = maxDiscountAmount,
            useStartDate = useStartDate,
            useEndDate = useEndDate,
            issuableQuantity = issuableQuantity
        )
    }

    companion object {
        fun from(coupon: Coupon): CreateCouponRequest {
            return coupon.run {
                CreateCouponRequest(
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
