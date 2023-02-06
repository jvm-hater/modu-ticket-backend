package com.jvmhater.moduticket.model

import com.jvmhater.moduticket.model.vo.ConcertCategory
import com.jvmhater.moduticket.util.generateId
import java.time.LocalDateTime

object ConcertFixture {
    fun generate(
        id: String = generateId(Concert.ID_PREFIX),
        name: String = "concert-name",
        place: String = "place",
        startDate: LocalDateTime = LocalDateTime.now(),
        time: Int = 100,
        category: ConcertCategory = ConcertCategory.values().random(),
        seats: List<Seat> = emptyList()
    ): Concert =
        Concert(
            id = id,
            name = name,
            place = place,
            startDate = startDate,
            time = time,
            category = category,
            seats = seats
        )
}
