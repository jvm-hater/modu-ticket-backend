package com.jvmhater.moduticket.elasticsearch.index

import java.time.Instant

@Suppress("ClassName")
object ConcertFieldSet {
    object id : IndexField<String>("id")
    object name : IndexField<String>("name")
    object place : IndexField<String>("place")
    object startDate : IndexField<Instant>("start_date")
    object time : IndexField<Int>("time")
    object category : IndexField<String>("category")
    object createdAt : IndexField<Instant>("created_at")
    object modifiedAt : IndexField<Instant>("modified_at")
}
