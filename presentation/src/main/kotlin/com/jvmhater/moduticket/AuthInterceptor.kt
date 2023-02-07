package com.jvmhater.moduticket

import com.jvmhater.moduticket.exception.DomainException
import com.jvmhater.moduticket.service.UserService
import io.jsonwebtoken.Jwts
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import org.springframework.web.util.pattern.PathPattern
import org.springframework.web.util.pattern.PathPatternParser
import reactor.core.publisher.Mono
import java.util.Base64

@Component
class AuthInterceptor : WebFilter {
    companion object {
        private const val BEARER_HEADER = "Bearer"
        private const val SIGNING_KEY = "bW9kdS10aWNrZXQ="
        private const val ID = "id"
        private val routes = hashSetOf<String>().apply {
            add("/api/signup")
            add("/api/login")
        }
    }

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {

        if (!routes.contains(exchange.request.path.toString())) {
            val authHeader = exchange.request.headers[HttpHeaders.AUTHORIZATION]?.first()
            try {
                if (authHeader != null) {
                    val token = authHeader.substring(BEARER_HEADER.length)
                    val body = Jwts.parser().setSigningKey(SIGNING_KEY).parseClaimsJws(token).body
                    body[ID] ?: throw DomainException.UnauthorizedRequestException(message = "인증되지 않은 요청입니다.")
                }
            } catch (e: Exception) {
                throw DomainException.UnauthorizedRequestException(message = "인증되지 않은 요청입니다.")
            }
        }
        return chain.filter(exchange)
    }

}