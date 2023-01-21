package com.jvmhater.moduticket.model

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("modu")
class ModuRow(@Id @Column("id") override val rowId: String, val name: String) : Row<String> {

    @Transient
    override var isNewRow: Boolean = false
        internal set

    fun toDomain(): Modu = Modu(id = id, name = name)
}

fun Modu.toRow(isNewRow: Boolean = false): ModuRow {
    val row = ModuRow(rowId = id, name = name)
    row.isNewRow = isNewRow
    return row
}
