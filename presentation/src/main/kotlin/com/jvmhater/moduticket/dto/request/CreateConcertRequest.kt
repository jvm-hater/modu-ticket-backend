package com.jvmhater.moduticket.dto.request

import com.jvmhater.moduticket.model.Concert
import com.jvmhater.moduticket.model.vo.ConcertCategory
import java.time.LocalDateTime

data class CreateConcertRequest(
    val name: String,
    val place: String,
    val startDate: LocalDateTime,
    val time: Int,
    val category: ConcertCategory,
    val seats: List<CreateSeatRequest>
) {

    fun toDomain(): Concert =
        Concert(
            name = name,
            place = place,
            startDate = startDate,
            time = time,
            category = category,
            seats = seats.toDomains()
        )
}
