package com.jvmhater.moduticket.client

import com.jvmhater.moduticket.model.Card
import com.jvmhater.moduticket.model.Payment

interface PaymentClient {
    suspend fun payWithCard(card: Card): Payment

    suspend fun cancel(pgPaymentId: String): Payment
}
