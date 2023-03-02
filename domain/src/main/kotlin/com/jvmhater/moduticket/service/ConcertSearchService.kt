package com.jvmhater.moduticket.service

import com.jvmhater.moduticket.model.Concert
import com.jvmhater.moduticket.model.query.ConcertSearchQuery
import com.jvmhater.moduticket.model.query.Page

interface ConcertSearchService {
    suspend fun search(query: ConcertSearchQuery, page: Page): List<Concert>
}
