package com.jvmhater.moduticket.service

import com.jvmhater.moduticket.repository.AuthRepository
import org.springframework.stereotype.Service

@Service
class AuthService(private val authRepository: AuthRepository) {

    suspend fun login(id: String, password: String) {
        return authRepository.login(id, password)
    }


}
