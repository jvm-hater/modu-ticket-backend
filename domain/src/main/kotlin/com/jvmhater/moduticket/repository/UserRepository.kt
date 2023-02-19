package com.jvmhater.moduticket.repository

import com.jvmhater.moduticket.model.User

interface UserRepository {
    suspend fun create(userId: String, password: String)

    suspend fun find(userId: String): User

    suspend fun findAll(): List<User>

    suspend fun findWithIssuedCoupon(userId: String): User

    suspend fun delete(userId: String)
}
