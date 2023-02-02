package com.jvmhater.moduticket.dto.response

import com.jvmhater.moduticket.model.vo.SeatTier

data class SeatResponse(
    val id: String,
    val tier: SeatTier,
    val amount: Long,
    val isSoldOut: Boolean
)
