package com.jvmhater.moduticket

import io.jsonwebtoken.Header
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component

@Component
class JwtProvider(
    private val jwtSecret: String
) {
    fun createJwt(id: String): String {
        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .claim("id", id)
            .signWith(SignatureAlgorithm.HS256, jwtSecret)  // 보안측면으로는 ES256 사용하는 것이 더 좋음.
            .compact()
    }
}