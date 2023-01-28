package com.jvmhater.moduticket.repository

interface AuthRepository {
    suspend fun login(id : String, password: String)
}