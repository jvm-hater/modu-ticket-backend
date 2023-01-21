package com.jvmhater.moduticket.model

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("modu")
data class ModuRow(@Id @Column("id") override val rowId: String, val name: String) : Row<String> {
    @Transient
    override var isNewRow: Boolean = true
        private set

    constructor(rowId: String, name: String, flag: Boolean) : this(rowId, name) {
        isNewRow = flag
    }

    fun toDomain(): Modu = Modu(id = id, name = name)

}

fun Modu.toRow(isNewRow: Boolean = false): ModuRow {
    val row = ModuRow(rowId = id, name = name, flag = isNewRow)
    return row
}