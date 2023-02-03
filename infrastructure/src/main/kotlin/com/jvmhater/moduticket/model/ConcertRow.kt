package com.jvmhater.moduticket.model

import com.jvmhater.moduticket.model.vo.ConcertCategory
import java.time.LocalDateTime
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("concert")
data class ConcertRow(
    @Id @Column("id") override val rowId: String,
    @Transient @Value("null") override val isNewRow: Boolean = false,
    val name: String,
    val place: String,
    val startDate: LocalDateTime,
    val time: Int,
    val category: ConcertCategory
) : Row<String>

fun ConcertRow.toDomain(): Concert =
    Concert(
        id = id,
        name = name,
        place = place,
        startDate = startDate,
        time = time,
        category = category
    )

fun List<ConcertRow>.toDomains(): List<Concert> = this.map { it.toDomain() }

fun Concert.toRow(isNewRow: Boolean = false): ConcertRow =
    ConcertRow(
        rowId = id,
        isNewRow = isNewRow,
        name = name,
        place = place,
        startDate = startDate,
        time = time,
        category = category
    )
