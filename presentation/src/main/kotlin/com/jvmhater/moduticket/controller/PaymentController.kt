package com.jvmhater.moduticket.controller

import com.jvmhater.moduticket.dto.request.PayWithCardRequest
import com.jvmhater.moduticket.dto.request.toCard
import com.jvmhater.moduticket.model.Payment
import com.jvmhater.moduticket.model.vo.toAmount
import com.jvmhater.moduticket.service.PaymentService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class PaymentController(private val paymentService: PaymentService) {
    @Operation(description = "카드 번호로 비인증 결제를 한다.")
    @PostMapping("/payments/card")
    suspend fun payWithCard(@RequestBody request: PayWithCardRequest): Payment =
        paymentService.payWithCard(
            card = request.toCard(),
            userId = request.userId,
            birthOrBusinessNumber = request.birthOrBusinessNumber,
            amount = request.amount.toAmount()
        )


    @Operation(description = "주문번호로 결제를 취소한다.")
    @PostMapping("/payments/cancel")
    suspend fun cancel(@RequestParam("paymentId") paymentId: String) {
        paymentService.cancel(
            paymentId = paymentId
        )
    }
}
