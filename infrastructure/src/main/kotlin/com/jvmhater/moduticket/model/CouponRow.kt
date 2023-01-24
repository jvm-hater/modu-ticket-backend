package com.jvmhater.moduticket.model

import org.springframework.beans.factory.annotation.Value
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("coupon")
class CouponRow(
    @Id @Column("id") override val rowId: String,
    @Transient @Value("null") override val isNewRow: Boolean = false,
    val name: String,
    val discountRate: Double,
    val concertCategories: List<ConcertCategory>,
    val maxDiscountAmount: Int,
    val useStartDate: LocalDateTime,
    val usedEndDate: LocalDateTime,
    val issuableQuantity: Int,
) : Row<String> {

    fun toDomain(): Coupon = Coupon(
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

fun Coupon.toRow(isNewRow: Boolean = false): CouponRow {
    return CouponRow(
        rowId = id,
        name = name,
        isNewRow = isNewRow,
        discountRate = discountRate,
        concertCategories = concertCategories,
        maxDiscountAmount = maxDiscountAmount,
        useStartDate = useStartDate,
        usedEndDate = usedEndDate,
        issuableQuantity = issuableQuantity
    )
}