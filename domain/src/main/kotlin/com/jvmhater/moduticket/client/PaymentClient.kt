package com.jvmhater.moduticket.client

import com.jvmhater.moduticket.model.Card
import com.jvmhater.moduticket.model.Payment
import com.jvmhater.moduticket.model.vo.Amount

interface PaymentClient {
    suspend fun payWithCard(card: Card, birthOrBusinessNumber: String, amount: Amount): Payment

    suspend fun cancel(pgTransactionId: String)
}
