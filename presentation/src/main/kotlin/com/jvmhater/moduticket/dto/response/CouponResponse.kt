package com.jvmhater.moduticket.dto.response

import com.jvmhater.moduticket.model.Coupon
import com.jvmhater.moduticket.model.vo.ConcertCategory
import com.jvmhater.moduticket.model.vo.toQuantity
import java.time.LocalDateTime

data class CouponResponse(
    val id: String,
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
            id = id,
            name = name,
            discountRate = discountRate,
            concertCategory = concertCategory,
            maxDiscountAmount = maxDiscountAmount,
            useStartDate = useStartDate,
            useEndDate = useEndDate,
            issuableQuantity = issuableQuantity.toQuantity()
        )
    }
}

fun Coupon.toResponse(): CouponResponse =
    CouponResponse(
        id = id,
        name = name,
        discountRate = discountRate,
        concertCategory = concertCategory,
        maxDiscountAmount = maxDiscountAmount,
        useStartDate = useStartDate,
        useEndDate = useEndDate,
        issuableQuantity = issuableQuantity.value
    )

fun List<Coupon>.toResponses(): List<CouponResponse> = this.map { it.toResponse() }
