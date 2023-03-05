package com.jvmhater.moduticket.service

import com.jvmhater.moduticket.client.PaymentClient
import com.jvmhater.moduticket.model.Card
import com.jvmhater.moduticket.model.Payment
import com.jvmhater.moduticket.model.vo.Amount
import com.jvmhater.moduticket.repository.SpringDataPaymentRepository
import com.jvmhater.moduticket.util.generateId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PaymentService(private val paymentClient: PaymentClient, private val paymentRepository: SpringDataPaymentRepository) {

    suspend fun payWithCard(
        card: Card,
        userId: String,
        birthOrBusinessNumber: String,
        amount: Amount
    ): Payment {
        val reservationId = generateId("reservation-")
        val response = paymentClient.payWithCard(card = card, birthOrBusinessNumber = birthOrBusinessNumber, amount = amount)

        return paymentRepository.create(payment = response, reservationId = reservationId)
    }


    suspend fun cancel(
        paymentId: String
    ) {
        val payment = paymentRepository.find(paymentId = paymentId)
        paymentClient.cancel(pgTransactionId = payment.pgTransactionId)
    }
}
