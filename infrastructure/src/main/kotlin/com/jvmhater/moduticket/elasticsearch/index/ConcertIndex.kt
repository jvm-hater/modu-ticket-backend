package com.jvmhater.moduticket.elasticsearch.index

import com.fasterxml.jackson.annotation.JsonProperty
import com.jvmhater.moduticket.model.Concert
import com.jvmhater.moduticket.model.vo.ConcertCategory
import com.jvmhater.moduticket.util.toLocalDateTime
import java.time.Instant

// TODO : ConcertFieldSet 과 필드이름을 중복해서 정의하고 있다.
data class ConcertIndex(
    @JsonProperty("id") val id: String,
    @JsonProperty("name") val name: String,
    @JsonProperty("place") val place: String,
    @JsonProperty("start_date") val startDate: Instant,
    @JsonProperty("time") val time: Int,
    @JsonProperty("category") val category: ConcertCategory,
    @JsonProperty("created_at") val createdAt: Instant,
    @JsonProperty("modified_at") val modifiedAt: Instant
) {
    fun toDomain(): Concert =
        Concert(
            id = id,
            name = name,
            place = place,
            startDate = startDate.toLocalDateTime(),
            time = time,
            category = category,
        )
}
