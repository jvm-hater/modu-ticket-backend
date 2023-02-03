package com.jvmhater.moduticket.repository

import com.jvmhater.moduticket.model.Concert
import com.jvmhater.moduticket.model.query.ConcertSearchQuery
import com.jvmhater.moduticket.model.query.Page

interface ConcertRepository {
    suspend fun findConcerts(query: ConcertSearchQuery, page: Page): List<Concert>
    suspend fun find(id: String): Concert
    suspend fun create(concert: Concert): Concert
    suspend fun delete(id: String)
}
