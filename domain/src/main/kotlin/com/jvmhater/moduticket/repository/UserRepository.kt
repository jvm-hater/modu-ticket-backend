package com.jvmhater.moduticket.repository

import com.jvmhater.moduticket.model.User

interface UserRepository {
    suspend fun create(id: String, password: String)

    suspend fun find(id: String): User

    suspend fun findAll(): List<User>

    suspend fun delete(id: String)
}
