package com.jvmhater.moduticket.controller

import com.jvmhater.moduticket.dto.request.PayWithCardRequest
import com.jvmhater.moduticket.dto.request.toCard
import com.jvmhater.moduticket.service.PaymentService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class PaymentController(private val paymentService: PaymentService) {
    @Operation(description = "카드 번호로 비인증 결제를 한다.")
    @PostMapping("/payments/card")
    suspend fun payWithCard(@RequestBody request: PayWithCardRequest) {
        paymentService.payWithCard(
            card = request.toCard(),
            concertId = request.concertId,
            userId = request.userId,
            birthOrBusinessNumber = request.birthOrBusinessNumber
        )
    }
}
