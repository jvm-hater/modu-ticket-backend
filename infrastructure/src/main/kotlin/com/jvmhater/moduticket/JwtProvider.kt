package com.jvmhater.moduticket

import com.jvmhater.moduticket.repository.TokenProvider
import io.jsonwebtoken.Header
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class JwtProvider(@Value("\${jwt.secret}") private val jwtSecret: String) : TokenProvider {
    override fun createToken(id: String): String {
        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .claim("id", id)
            .signWith(SignatureAlgorithm.HS256, jwtSecret) // 보안측면으로는 ES256 사용하는 것이 더 좋음.
            .compact()
    }
}
