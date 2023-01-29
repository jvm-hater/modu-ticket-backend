package com.jvmhater.moduticket.repository

import com.jvmhater.moduticket.model.UserRow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository interface R2dbcUserRepository : CoroutineCrudRepository<UserRow, String>
