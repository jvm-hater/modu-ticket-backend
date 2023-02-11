package com.jvmhater.moduticket.dto.request

import com.jvmhater.moduticket.model.vo.ConcertCategory
import java.time.LocalDateTime

object CreateConcertRequestFixture {
    fun generate(
        name: String = "name",
        place: String = "place",
        startDate: LocalDateTime = LocalDateTime.now(),
        time: Int = 100,
        category: ConcertCategory = ConcertCategory.values().random(),
        seats: List<CreateSeatRequest> = emptyList()
    ): CreateConcertRequest =
        CreateConcertRequest(
            name = name,
            place = place,
            startDate = startDate,
            time = time,
            category = category,
            seats = seats
        )
}
