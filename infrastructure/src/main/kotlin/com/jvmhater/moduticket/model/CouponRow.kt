package com.jvmhater.moduticket.model

import com.jvmhater.moduticket.model.vo.ConcertCategory
import java.time.LocalDateTime
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("coupon")
class CouponRow(
    @Id @Column("id") override val rowId: String,
    @Transient @Value("null") override val isNewRow: Boolean = false,
    val name: String,
    val discountRate: Int,
    val concertCategory: ConcertCategory,
    val maxDiscountAmount: Int,
    val useStartDate: LocalDateTime,
    val useEndDate: LocalDateTime,
    val issuableQuantity: Int,
) : Row<String> {

    fun toDomain(): Coupon =
        Coupon(
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

fun Coupon.toRow(isNewRow: Boolean = false): CouponRow {
    return CouponRow(
        rowId = id,
        name = name,
        isNewRow = isNewRow,
        discountRate = discountRate,
        concertCategory = concertCategory,
        maxDiscountAmount = maxDiscountAmount,
        useStartDate = useStartDate,
        useEndDate = useEndDate,
        issuableQuantity = issuableQuantity
    )
}
