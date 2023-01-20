package com.jvmhater.moduticket.repository

import com.jvmhater.moduticket.model.Modu

interface ModuRepository {
    suspend fun save(modu: Modu): Modu
    suspend fun findById(id: String): Modu
    suspend fun update(modu: Modu): Modu
}
