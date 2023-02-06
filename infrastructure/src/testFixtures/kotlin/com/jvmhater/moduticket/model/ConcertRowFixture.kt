package com.jvmhater.moduticket.model

import com.jvmhater.moduticket.model.vo.ConcertCategory
import com.jvmhater.moduticket.util.generateId
import java.time.LocalDateTime

object ConcertRowFixture {
    fun generate(
        rowId: String = generateId(Concert.ID_PREFIX),
        name: String = "concert-name",
        place: String = "place",
        startDate: LocalDateTime = LocalDateTime.now(),
        time: Int = 100,
        category: ConcertCategory = ConcertCategory.values().random(),
    ): ConcertRow =
        ConcertRow(
            rowId = rowId,
            name = name,
            place = place,
            startDate = startDate,
            time = time,
            category = category
        )
}
