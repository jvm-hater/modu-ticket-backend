package com.jvmhater.moduticket.dto.response

import com.jvmhater.moduticket.model.Concert
import com.jvmhater.moduticket.model.vo.ConcertCategory
import java.time.LocalDateTime

data class ConcertResponse(
    val id: String,
    val name: String,
    val place: String,
    val startDate: LocalDateTime,
    val time: Int,
    val category: ConcertCategory,
    val seats: List<SeatResponse>
)

fun Concert.toResponse(): ConcertResponse =
    ConcertResponse(
        id = id,
        name = name,
        place = place,
        startDate = startDate,
        time = time,
        category = category,
        seats = seats.toResponses()
    )
