package com.jvmhater.moduticket.model

import com.jvmhater.moduticket.model.vo.SeatTier
import com.jvmhater.moduticket.model.vo.toAmount
import com.jvmhater.moduticket.model.vo.toQuantity
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

fun SeatRow.toDomain(): Seat =
    Seat(
        id = id,
        tier = tier,
        amount = amount.toAmount(),
        totalQuantity = totalQuantity.toQuantity(),
        quantityLeft = quantityLeft.toQuantity()
    )

fun List<SeatRow>.toDomains(): List<Seat> = this.map { it.toDomain() }

fun Seat.toRow(isNewRow: Boolean = false, concertId: String): SeatRow =
    SeatRow(
        rowId = id,
        isNewRow = isNewRow,
        concertId = concertId,
        tier = tier,
        amount = amount.value,
        totalQuantity = totalQuantity.value,
        quantityLeft = quantityLeft.value
    )

fun List<Seat>.toRows(isNewRow: Boolean = false, concertId: String): List<SeatRow> =
    this.map { it.toRow(isNewRow = isNewRow, concertId = concertId) }
