package com.jvmhater.moduticket.service

import com.jvmhater.moduticket.model.Concert
import com.jvmhater.moduticket.model.query.ConcertSearchQuery
import com.jvmhater.moduticket.model.query.Page
import com.jvmhater.moduticket.repository.ConcertRepository
import org.springframework.stereotype.Service

@Service
// @Transactional
class ConcertService(private val concertRepository: ConcertRepository) {

    // @Transactional(readOnly = true)
    suspend fun findConcerts(query: ConcertSearchQuery, page: Page): List<Concert> {
        return concertRepository.findConcerts(query, page)
    }

    // @Transactional(readOnly = true)
    suspend fun find(id: String): Concert {
        return concertRepository.find(id)
    }

    suspend fun create(concert: Concert): Concert {
        return concertRepository.create(concert)
    }

    suspend fun delete(id: String) {
        concertRepository.delete(id)
    }
}
