package com.jvmhater.moduticket.model

import org.springframework.beans.factory.annotation.Value
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("payment")
data class PaymentRow(
    @Id @Column("id") override val rowId: String,
    @Transient @Value("null") override val isNewRow: Boolean = false,
    val paymentId: String, // PG사에서 결제건에 부여한 ID (취소 시 사용됨)
    val userId: String,
    val concertId: String,
    val approvedAmount: UInt,
) : Row<String>
