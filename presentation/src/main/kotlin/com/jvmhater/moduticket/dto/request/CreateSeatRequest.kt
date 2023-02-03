package com.jvmhater.moduticket.dto.request

import com.jvmhater.moduticket.model.Seat
import com.jvmhater.moduticket.model.vo.SeatTier
import com.jvmhater.moduticket.model.vo.toAmount
import com.jvmhater.moduticket.model.vo.toQuantity

data class CreateSeatRequest(
    val tier: SeatTier,
    val amount: Long,
    val totalQuantity: Int,
    val quantityLeft: Int
)

fun CreateSeatRequest.toDomain(): Seat =
    Seat(
        tier = tier,
        amount = amount.toAmount(),
        totalQuantity = totalQuantity.toQuantity(),
        quantityLeft = quantityLeft.toQuantity()
    )

fun List<CreateSeatRequest>.toDomains(): List<Seat> = this.map { it.toDomain() }
