package com.jvmhater.moduticket.repository

import com.jvmhater.moduticket.model.User

interface UserRepository {

    suspend fun findWithIssuedCoupon(id: String): User
}
