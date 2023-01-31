package com.jvmhater.moduticket.service

import com.jvmhater.moduticket.model.Concert
import com.jvmhater.moduticket.repository.ConcertRepository
import com.jvmhater.moduticket.repository.SeatRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ConcertService(
    private val concertRepository: ConcertRepository,
    private val seatRepository: SeatRepository
) {

    suspend fun findConcerts(): List<Concert> = TODO()

    suspend fun find(): Concert = TODO()

    suspend fun create(): Concert = TODO()

    suspend fun delete(): Unit = TODO()
}
