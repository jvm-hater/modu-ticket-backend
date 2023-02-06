package com.jvmhater.moduticket.model

import com.jvmhater.moduticket.model.vo.Amount
import com.jvmhater.moduticket.model.vo.Quantity
import com.jvmhater.moduticket.model.vo.SeatTier
import com.jvmhater.moduticket.util.generateId

object SeatFixture {
    fun generate(
        id: String = generateId(Seat.ID_PREFIX),
        tier: SeatTier = SeatTier.values().random(),
        amountValue: Long = 100,
        totalQuantityValue: Int = 100,
        quantityLeftValue: Int = 100
    ): Seat =
        Seat(
            id = id,
            tier = tier,
            amount = Amount(value = amountValue),
            totalQuantity = Quantity(value = totalQuantityValue),
            quantityLeft = Quantity(value = quantityLeftValue)
        )
}
