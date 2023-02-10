package com.jvmhater.moduticket.dto.request

import com.jvmhater.moduticket.model.vo.ConcertCategory

data class ViewConcertsRequest(
    val category: ConcertCategory,
    val searchText: String,
    val page: Long,
    val size: Int
)
