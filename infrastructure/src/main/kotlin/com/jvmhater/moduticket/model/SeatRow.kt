package com.jvmhater.moduticket.model

import com.jvmhater.moduticket.model.vo.SeatTier
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("seat")
data class SeatRow(
    @Id @Column("id") override val rowId: String,
    @Transient @Value("null") override val isNewRow: Boolean = false,
    val concertId: String,
    val tier: SeatTier,
    val amount: Long,
    val totalQuantity: Int,
    val quantityLeft: Int
) : Row<String>
