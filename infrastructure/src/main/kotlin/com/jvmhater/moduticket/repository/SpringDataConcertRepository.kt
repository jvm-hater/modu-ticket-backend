package com.jvmhater.moduticket.repository

import com.jvmhater.moduticket.model.Concert
import org.springframework.stereotype.Repository

@Repository
class SpringDataConcertRepository : ConcertRepository {
    override suspend fun findConcerts(): List<Concert> {
        TODO("Not yet implemented")
    }

    override suspend fun find(id: String): Concert {
        TODO("Not yet implemented")
    }

    override suspend fun create(): Concert {
        TODO("Not yet implemented")
    }

    override suspend fun delete() {
        TODO("Not yet implemented")
    }
}

@Repository interface R2dbcConcertRepository
