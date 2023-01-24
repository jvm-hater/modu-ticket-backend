package com.jvmhater.moduticket.model

object ModuRowFixture {
    fun generate(rowId: String = "", name: String = "", isNewRow: Boolean = false): ModuRow =
        ModuRow(rowId = rowId, name = name, isNewRow = isNewRow)
}
