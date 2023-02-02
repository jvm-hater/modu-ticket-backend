package com.jvmhater.moduticket.service

import com.jvmhater.moduticket.model.User
import com.jvmhater.moduticket.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    suspend fun signUp(id: String, password: String) {
        return userRepository.create(id, password)
    }

    suspend fun find(id: String): User {
        return userRepository.find(id)
    }

    suspend fun findAll(): List<User> {
        return userRepository.findAll()
    }

    suspend fun delete(id: String) {
        return userRepository.delete(id)
    }
}
