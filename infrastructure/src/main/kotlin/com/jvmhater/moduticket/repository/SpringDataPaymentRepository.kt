package com.jvmhater.moduticket.repository

import com.jvmhater.moduticket.model.PaymentRow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository class SpringDataPaymentRepository() : PaymentRepository {}

@Repository interface R2dbcPaymentRepository : CoroutineCrudRepository<PaymentRow, String>
