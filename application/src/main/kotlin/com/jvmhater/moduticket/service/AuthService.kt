package com.jvmhater.moduticket.service

import com.jvmhater.moduticket.exception.DomainException
import com.jvmhater.moduticket.repository.TokenProvider
import com.jvmhater.moduticket.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val tokenProvider: TokenProvider
) {

    suspend fun login(userId: String, password: String): String {
        val user = userRepository.find(userId)
        if (user.password != password) {
            throw DomainException.InvalidArgumentException(message = "비밀번호가 일치하지 않습니다.")
        }
        return tokenProvider.createToken(user.id)
    }

    suspend fun signUp(userId: String, password: String) {
        return userRepository.create(userId, password)
    }
}
