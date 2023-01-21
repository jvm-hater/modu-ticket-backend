package com.jvmhater.moduticket.model

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("modu")
class ModuRow(@Id @Column("id") override val rowId: String, val name: String) : Row<String> {

    @Transient override var isNewRow: Boolean = false

    fun toDomain(): Modu = Modu(id = id, name = name)

    companion object {
        /*
        Entity의 ID 값을 설정하여 넣어주어야 합니다.
         */
        fun toInsert(modu: Modu): ModuRow {
            val moduEntity = modu.toRow()
            moduEntity.isNewRow = true
            return moduEntity
        }

        /*
        기존 Entity의 ID가 변경되어 들어오면 예외가 발생합니다.
         */
        fun toUpdate(modu: Modu): ModuRow {
            val moduEntity = modu.toRow()
            moduEntity.isNewRow = false
            return moduEntity
        }
    }
}

fun Modu.toRow(): ModuRow = ModuRow(rowId = id, name = name)
