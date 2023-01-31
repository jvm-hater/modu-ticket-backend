package com.jvmhater.moduticket.repository

import com.jvmhater.moduticket.model.Seat

interface SeatRepository {
    suspend fun find(): List<Seat>
    suspend fun update(): Seat
}
