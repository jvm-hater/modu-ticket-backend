package com.jvmhater.moduticket.repository

import com.jvmhater.moduticket.exception.RepositoryException
import com.jvmhater.moduticket.model.Payment
import com.jvmhater.moduticket.model.PaymentRow
import com.jvmhater.moduticket.model.toDomain
import com.jvmhater.moduticket.model.toRow
import com.jvmhater.moduticket.util.ifNullThrow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
class SpringDataPaymentRepository(
    private val r2dbcPaymentRepository: R2dbcPaymentRepository
) : PaymentRepository {
    override suspend fun create(payment: Payment, reservationId: String): Payment =
        r2dbcPaymentRepository.save(
            PaymentRow(
                isNewRow = true,
                rowId = payment.paymentId,
                pgTransactionId = payment.pgTransactionId,
                approvedAmount = payment.amount,
                reservationId = reservationId
            )
        ).toDomain()

    override suspend fun update(payment: Payment, reservationId: String): Payment =
        r2dbcPaymentRepository.save(
            PaymentRow(
                isNewRow = false,
                rowId = payment.paymentId,
                pgTransactionId = payment.pgTransactionId,
                approvedAmount = payment.amount,
                reservationId = reservationId
            )
        ).toDomain()

    override suspend fun find(paymentId: String): Payment =
        r2dbcPaymentRepository.findById(paymentId).ifNullThrow(
            ifNotNull = {
                println(it)
                it.toDomain()
            },
            exception = RepositoryException.RecordNotFound(message = "존재하지 않은 결제 건 ID 입니다.")
        )


}

@Repository
interface R2dbcPaymentRepository : CoroutineCrudRepository<PaymentRow, String>
