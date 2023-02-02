package com.jvmhater.moduticket.repository

import com.jvmhater.moduticket.model.Concert

interface ConcertRepository {
    suspend fun findConcerts(): List<Concert>
    suspend fun find(id: String): Concert
    suspend fun create(): Concert
    suspend fun delete()
}
