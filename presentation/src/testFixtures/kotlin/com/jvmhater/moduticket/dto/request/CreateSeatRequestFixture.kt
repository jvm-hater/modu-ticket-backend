package com.jvmhater.moduticket.dto.request

import com.jvmhater.moduticket.model.vo.SeatTier

object CreateSeatRequestFixture {
    fun generate(
        tier: SeatTier = SeatTier.values().random(),
        amount: Long = 100,
        totalQuantity: Int = 10,
        quantityLeft: Int = 1
    ): CreateSeatRequest =
        CreateSeatRequest(
            tier = tier,
            amount = amount,
            totalQuantity = totalQuantity,
            quantityLeft = quantityLeft
        )
}
