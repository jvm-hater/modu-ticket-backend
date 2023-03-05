package com.jvmhater.moduticket.client

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.jvmhater.moduticket.model.Card
import com.jvmhater.moduticket.model.Payment
import com.jvmhater.moduticket.model.vo.Amount
import com.jvmhater.moduticket.util.generateId
import com.jvmhater.moduticket.util.toObject
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitExchange
import reactor.core.publisher.Mono

@Repository
class PortonePaymentClient(
    @Qualifier("portoneWebclient") private val webclient: WebClient,
    @Value("\${portone.api-key}") private val apiKey: String,
    @Value("\${portone.api-secret}") private val apiSecret: String,
) : PaymentClient {
    private suspend inline fun <reified T : Any> requestWithAuth(
        block: WebClient.() -> WebClient.RequestHeadersSpec<*>
    ): T {
        try {

            val accessToken: String = webclient.post().uri("users/getToken").bodyValue(
                AccessTokenRequest(
                    impKey = apiKey,
                    impSecret = apiSecret
                )
            ).awaitExchange {
                if (it.statusCode().is2xxSuccessful) {
                    val rawBody = it.awaitBody<String>()
                    println(rawBody)
                    return@awaitExchange rawBody.toObject<AccessTokenResponse>().response.accessToken
                } else {
                    throw RuntimeException()
                }
            }

            val response = webclient.block().headers { it.setBearerAuth(accessToken) }.awaitExchange {
                if (it.statusCode().is2xxSuccessful) {
                    val rawBody = it.awaitBody<String>()
                    println(rawBody)
                    rawBody.toObject<T>()
                } else {
                    throw RuntimeException()
                }
            }
            return response
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException()
        }
    }

    override suspend fun payWithCard(card: Card, birthOrBusinessNumber: String, amount: Amount): Payment {
        val paymentId = generateId("modu-payment-")

        val response = requestWithAuth<PayWithCardResponse> {
            webclient
                .post()
                .uri("/subscribe/payments/onetime")
                .bodyValue(
                    PayWithCardRequest(
                        merchantUid = paymentId,
                        expiry = createExpirationString(expYear = card.expiryYear, expMonth = card.expiryMonth),
                        birth = birthOrBusinessNumber,
                        cardNumber = card.number,
                        pg = "kcp.BA001",
                        amount = amount.value
                    )
                )
        }.response

        return Payment(paymentId = paymentId, amount = response.amount.toLong(), pgTransactionId = response.impUid)
    }

    private fun createExpirationString(expYear: Int, expMonth: Int): String {
        val yearString = "20${expYear}"
        val monthString = if (expMonth < 10) "0$expMonth" else expMonth.toString()
        return "$yearString-$monthString"
    }


    override suspend fun cancel(pgTransactionId: String) {
        val response = requestWithAuth<CancelResponse> {
            webclient
                .post()
                .uri("/payments/cancel")
                .bodyValue(
                    CancelRequest(
                        impUid = pgTransactionId
                    )
                )
        }.response
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class AccessTokenRequest(
        val impKey: String,
        val impSecret: String
    )
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class CancelRequest(
        val impUid: String
    )
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class CancelResponse(
        val response: CancelDetailResponse
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class CancelDetailResponse(
        val amount: Long,
        val cancelledAt: Long
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class AccessTokenResponse(
        val response: AccessTokenDetailResponse
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class AccessTokenDetailResponse(
        val accessToken: String
    )

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class PayWithCardRequest(
        val merchantUid: String,
        val birth: String,
        val pg: String,
        val expiry: String,
        val cardNumber: String,
        val amount: Long
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class PayWithCardResponse(
        val code: Int,
        val message: String?,
        val response: PaymentResponse
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class PaymentResponse(val amount: Int, val impUid: String)
}

fun generateWebclient(baseUrl: String): WebClient =
    WebClient.builder()
        .baseUrl(baseUrl)
        //        .filter(logRequest())
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
