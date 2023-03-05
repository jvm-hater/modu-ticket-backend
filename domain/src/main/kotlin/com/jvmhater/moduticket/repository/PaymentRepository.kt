package com.jvmhater.moduticket.repository

import com.jvmhater.moduticket.model.Payment

interface PaymentRepository {
    suspend fun create(payment: Payment, reservationId : String) : Payment

    suspend fun update(payment: Payment, reservationId : String) : Payment

    suspend fun find(paymentId: String) : Payment
}
