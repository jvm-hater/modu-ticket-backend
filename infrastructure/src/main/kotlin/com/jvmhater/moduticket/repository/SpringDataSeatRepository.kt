package com.jvmhater.moduticket.repository

import com.jvmhater.moduticket.model.Seat
import com.jvmhater.moduticket.model.SeatRow
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
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

@Repository
interface R2dbcSeatRepository : CoroutineCrudRepository<SeatRow, String> {
    fun findByConcertId(concertId: String): Flow<SeatRow>
    fun deleteByConcertId(concertId: String)
}
