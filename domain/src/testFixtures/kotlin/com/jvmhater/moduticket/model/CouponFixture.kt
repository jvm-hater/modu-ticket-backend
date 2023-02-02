package com.jvmhater.moduticket.model

import com.jvmhater.moduticket.model.vo.ConcertCategory
import com.jvmhater.moduticket.util.generateId
import java.time.LocalDateTime

object CouponFixture {
    fun generate(
        id: String = generateId(Coupon.ID_PREFIX),
        name: String = "coupon-name",
        discountRate: Int = 10,
        concertCategory: ConcertCategory = ConcertCategory.BALLAD,
        maxDiscountAmount: Int = 10000,
        useStartDate: LocalDateTime = LocalDateTime.now(),
        useEndDate: LocalDateTime = useStartDate.plusDays(1L),
        issuableQuantity: Int = 100
    ): Coupon {
        return Coupon(
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
