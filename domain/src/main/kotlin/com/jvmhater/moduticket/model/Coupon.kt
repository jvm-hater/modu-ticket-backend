package com.jvmhater.moduticket.model

import com.jvmhater.moduticket.exception.DomainException
import com.jvmhater.moduticket.model.vo.ConcertCategory
import com.jvmhater.moduticket.model.vo.Quantity
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
    val issuableQuantity: Quantity,
) {
    companion object {
        const val ID_PREFIX = "coupon-"
        val MIN_ISSUABLE_QUANTITY = Quantity(1)
    }

    fun validateCouponUseDate() {
        val now = LocalDateTime.now()
        if (now.isBefore(useStartDate) || now.isAfter(useEndDate)) {
            throw DomainException.InvalidArgumentException("쿠폰의 발급 기한을 확인해주세요.")
        }
    }

    fun issue(): Coupon {
        if (issuableQuantity < MIN_ISSUABLE_QUANTITY) {
            throw DomainException.InvalidArgumentException("해당 쿠폰 발급은 마감되었습니다.")
        }
        return this.copy(issuableQuantity = issuableQuantity - 1)
    }
}
