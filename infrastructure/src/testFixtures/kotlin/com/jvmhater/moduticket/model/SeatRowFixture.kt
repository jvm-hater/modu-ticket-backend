package com.jvmhater.moduticket.model

import com.jvmhater.moduticket.model.vo.SeatTier
import com.jvmhater.moduticket.util.generateId

object SeatRowFixture {
    fun generate(
        rowId: String = generateId(Seat.ID_PREFIX),
        concertId: String,
        tier: SeatTier = SeatTier.values().random(),
        amount: Long = 100,
        totalQuantity: Int = 100,
        quantityLeft: Int = 100
    ): SeatRow =
        SeatRow(
            rowId = rowId,
            concertId = concertId,
            tier = tier,
            amount = amount,
            totalQuantity = totalQuantity,
            quantityLeft = quantityLeft
        )
}
