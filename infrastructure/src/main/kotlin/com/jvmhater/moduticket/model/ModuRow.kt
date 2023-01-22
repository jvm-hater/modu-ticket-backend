package com.jvmhater.moduticket.model

import org.springframework.beans.factory.annotation.Value
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("modu")
class ModuRow(
    @Id @Column("id") override val rowId: String,
    val name: String,
    @Transient @Value("null") override val isNewRow: Boolean = false,
) : Row<String> {

    fun toDomain(): Modu = Modu(id = id, name = name)
}

fun Modu.toRow(isNewRow: Boolean = false): ModuRow {
    return ModuRow(rowId = id, name = name, isNewRow = isNewRow)
}