package com.jvmhater.moduticket.model

import com.jvmhater.moduticket.model.vo.Amount
import com.jvmhater.moduticket.model.vo.Quantity
import com.jvmhater.moduticket.model.vo.SeatTier
import com.jvmhater.moduticket.util.generateId

data class Seat(
    val id: String = generateId(ID_PREFIX),
    val tier: SeatTier,
    val amount: Amount,
    val totalQuantity: Quantity,
    val quantityLeft: Quantity
) {
    companion object {
        const val ID_PREFIX = "seat-id-"
    }
}
