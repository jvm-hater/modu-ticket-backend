package com.jvmhater.moduticket.service

import com.jvmhater.moduticket.client.PaymentClient
import com.jvmhater.moduticket.model.Card
import org.springframework.stereotype.Service

@Service
class PaymentService(private val paymentClient: PaymentClient) {

    suspend fun payWithCard(
        card: Card,
        concertId: String,
        userId: String,
        birthOrBusinessNumber: String
    ) {
        paymentClient.payWithCard(card)
    }
}
