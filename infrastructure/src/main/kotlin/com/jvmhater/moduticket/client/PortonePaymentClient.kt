package com.jvmhater.moduticket.client

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.jvmhater.moduticket.model.Card
import com.jvmhater.moduticket.model.Payment
import com.jvmhater.moduticket.util.generateId
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitExchange
import reactor.core.publisher.Mono

@Repository
class PortonePaymentClient(@Qualifier("portoneWebclient") private val webclient: WebClient) :
    PaymentClient {
    override suspend fun payWithCard(card: Card): Payment =
        try {

            webclient
                .post()
                .uri("/subscribe/payments/onetime")
                .headers { it.setBearerAuth("96be4b3a57f09f84492e67168c463d3fbb53d0be") }
                .bodyValue(
                    PayWithCardRequest(
                        merchantUid = generateId("modu-payment-"),
                        expiry = "2027-11",
                        birth = "031225",
                        cardNumber = "5365103931649339",
                        pg = "kcp.BA001",
                        amount = 1000
                    )
                )
                .awaitExchange {
                    println(it.statusCode().is2xxSuccessful)
                    if (it.statusCode().is2xxSuccessful) {
                        println(it.awaitBody<PayWithCardResponse>())
                        Payment("aw", 1, "Asf")
                    } else {
                        throw RuntimeException()
                    }
                }
        } catch (e: RuntimeException) {
            e.printStackTrace()
            throw e
        }

    override suspend fun cancel(pgPaymentId: String): Payment {
        TODO("Not yet implemented")
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class PayWithCardRequest(
        val merchantUid: String,
        val birth: String,
        val pg: String,
        val expiry: String,
        val cardNumber: String,
        val amount: Long
    )

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class PayWithCardResponse(
        val code: Int,
        val message: String?,
        val response: PaymentResponse
    )

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class PaymentResponse(val amount: Int, val impUid: String)
}

fun generateWebclient(baseUrl: String): WebClient =
    WebClient.builder()
        .baseUrl(baseUrl)
        //        .filter(logRequest())
        .filter(logResponse())
        .build()

fun logRequest(): ExchangeFilterFunction =
    ExchangeFilterFunction.ofRequestProcessor { request ->
        println("${request.method()} ${request.url()}")
        Mono.just(request)
    }

fun logResponse(): ExchangeFilterFunction =
    ExchangeFilterFunction.ofResponseProcessor { response ->
        response.headers().asHttpHeaders().forEach { name, values ->
            values.forEach { println("$name = $it") }
        }
        Mono.just(response)
    }
