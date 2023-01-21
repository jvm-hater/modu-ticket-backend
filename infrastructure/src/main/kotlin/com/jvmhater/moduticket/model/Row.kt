package com.jvmhater.moduticket.model

import org.springframework.data.domain.Persistable

interface Row<ID> : Persistable<ID> {
    val rowId: ID
    var isNewRow: Boolean

    override fun getId(): ID = this.rowId
    override fun isNew(): Boolean = this.isNewRow
}
