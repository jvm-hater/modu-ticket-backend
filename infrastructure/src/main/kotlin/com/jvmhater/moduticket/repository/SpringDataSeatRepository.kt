package com.jvmhater.moduticket.repository

import com.jvmhater.moduticket.model.Seat
import org.springframework.stereotype.Repository

@Repository
class SpringDataSeatRepository : SeatRepository {
    override suspend fun find(): List<Seat> {
        TODO("Not yet implemented")
    }

    override suspend fun update(): Seat {
        TODO("Not yet implemented")
    }
}

@Repository interface R2dbcSeatRepository
