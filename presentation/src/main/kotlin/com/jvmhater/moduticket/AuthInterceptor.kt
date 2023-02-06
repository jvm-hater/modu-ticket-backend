package com.jvmhater.moduticket

import com.jvmhater.moduticket.exception.DomainException
import com.jvmhater.moduticket.service.UserService
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import java.util.Base64

@Component
class AuthInterceptor : WebFilter {
    companion object{
        private const val BASIC_HEADER = "Basic"
    }
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val authHeader = exchange.request.headers[HttpHeaders.AUTHORIZATION]?.first()
        if (authHeader != null) {
            if (authHeader.startsWith(BASIC_HEADER)) {
                val base64Credentials = authHeader.substring("Basic".length).trim()
                val credentials = String(Base64.getDecoder().decode(base64Credentials)).split(":")
                val username = credentials[0]
            }
        }
        else{
            throw DomainException.UnauthorizedRequestException(message = "인증되지 않은 요청입니다.")
        }
        return chain.filter(exchange)
    }

}