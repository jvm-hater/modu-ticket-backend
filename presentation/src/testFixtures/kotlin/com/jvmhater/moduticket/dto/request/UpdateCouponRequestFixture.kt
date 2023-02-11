package com.jvmhater.moduticket.dto.request

import com.jvmhater.moduticket.model.Coupon
import com.jvmhater.moduticket.model.vo.ConcertCategory
import java.time.LocalDateTime

object UpdateCouponRequestFixture {

    fun generate(
        name: String,
        discountRate: Int,
        concertCategory: ConcertCategory,
        maxDiscountAmount: Int,
        useStartDate: LocalDateTime,
        useEndDate: LocalDateTime,
        issuableQuantity: Int,
    ): UpdateCouponRequest =
        UpdateCouponRequest(
            name = name,
            discountRate = discountRate,
            concertCategory = concertCategory,
            maxDiscountAmount = maxDiscountAmount,
            useStartDate = useStartDate,
            useEndDate = useEndDate,
            issuableQuantity = issuableQuantity
        )

    fun generate(coupon: Coupon): UpdateCouponRequest =
        coupon.run {
            UpdateCouponRequest(
                name = name,
                discountRate = discountRate,
                concertCategory = concertCategory,
                maxDiscountAmount = maxDiscountAmount,
                useStartDate = useStartDate,
                useEndDate = useEndDate,
                issuableQuantity = issuableQuantity.value
            )
        }
}
