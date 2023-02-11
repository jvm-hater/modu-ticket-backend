package com.jvmhater.moduticket.model

import com.jvmhater.moduticket.util.generateId
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("issued_coupon")
class IssuedCouponRow(
    @Id @Column("id") override val rowId: String = generateId(ID_PREFIX),
    @Transient @Value("null") override val isNewRow: Boolean = false,
    val userId: String,
    val couponId: String
) : Row<String> {
    companion object {
        const val ID_PREFIX = "issued-coupon-"
    }
}
