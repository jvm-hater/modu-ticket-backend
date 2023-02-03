package com.jvmhater.moduticket.dto.response

import com.jvmhater.moduticket.model.Seat
import com.jvmhater.moduticket.model.vo.SeatTier

data class SeatResponse(
    val id: String,
    val tier: SeatTier,
    val amount: Long,
    val isSoldOut: Boolean
)

fun List<Seat>.toResponses(): List<SeatResponse> =
    this.map { seat ->
        SeatResponse(
            id = seat.id,
            tier = seat.tier,
            amount = seat.amount.value,
            isSoldOut = seat.isSoldOut()
        )
    }
