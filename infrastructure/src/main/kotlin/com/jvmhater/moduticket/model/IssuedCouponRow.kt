package com.jvmhater.moduticket.model

import com.jvmhater.moduticket.util.generateId
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("IssuedCoupon")
class IssuedCouponRow(
    @Id @Column("id") override val rowId: String = generateId("issued-coupon-"),
    @Transient @Value("null") override val isNewRow: Boolean = false,
    val userId: String,
    val couponId: String
) : Row<String> {
    companion object {
        fun of(isNewRow: Boolean = false, userId: String, couponId: String): IssuedCouponRow {
            return IssuedCouponRow(
                isNewRow = isNewRow,
                userId = userId,
                couponId = couponId
            )
        }
    }
}
