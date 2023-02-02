package com.jvmhater.moduticket.dto.request

import com.jvmhater.moduticket.model.vo.SeatTier

data class CreateSeatRequest(
    val tier: SeatTier,
    val amount: Long,
    val totalQuantity: Int,
    val quantityLeft: Int
)
