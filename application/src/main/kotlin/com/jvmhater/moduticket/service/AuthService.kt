package com.jvmhater.moduticket.service

import com.jvmhater.moduticket.JwtProvider
import com.jvmhater.moduticket.exception.DomainException
import com.jvmhater.moduticket.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class AuthService(private val userRepository: UserRepository, private val jwtProvider: JwtProvider) {

    suspend fun login(id: String, password: String) : String {
        val user = userRepository.find(id)
        if (user.password != password) {
            throw DomainException.InvalidArgumentException(message = "비밀번호가 일치하지 않습니다.")
        }
        return jwtProvider.createJwt(user.id)
    }

    suspend fun signUp(id: String, password: String) {
        return userRepository.create(id, password)
    }
}
