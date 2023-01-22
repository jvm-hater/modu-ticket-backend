package com.jvmhater.moduticket

import com.jvmhater.moduticket.model.ModuRow

fun genModuRow(rowId: String = "", name: String = "", isNewRow: Boolean = false): ModuRow =
    ModuRow(rowId = rowId, name = name, isNewRow = isNewRow)
