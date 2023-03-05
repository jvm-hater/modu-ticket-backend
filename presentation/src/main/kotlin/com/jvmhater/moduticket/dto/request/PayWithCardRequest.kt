package com.jvmhater.moduticket.dto.request

import com.jvmhater.moduticket.model.Card

data class PayWithCardRequest(
    val userId: String,
    val cardNumber: String,
    val expiryMonth: Int,
    val expiryYear: Int,
    val birthOrBusinessNumber: String,
    val amount: Long
)

fun PayWithCardRequest.toCard(): Card =
    Card(number = cardNumber, expiryMonth = expiryMonth, expiryYear = expiryYear)
