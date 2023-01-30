package com.jvmhater.moduticket.model

import com.jvmhater.moduticket.model.vo.ConcertCategory
import com.jvmhater.moduticket.util.generateId
import java.time.LocalDateTime

data class Concert(
    val id: String = generateId(Coupon.ID_PREFIX),
    val name: String,
    val place: String,
    val startDate: LocalDateTime,
    val time: Int,
    val category: ConcertCategory,
    val seats: List<Seat>
) {
    companion object {
        const val ID_PREFIX = "concert-id-"
    }
}
