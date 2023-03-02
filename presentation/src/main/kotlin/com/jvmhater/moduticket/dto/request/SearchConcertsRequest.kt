package com.jvmhater.moduticket.dto.request

import com.jvmhater.moduticket.model.vo.ConcertCategory

data class SearchConcertsRequest(
    val category: ConcertCategory,
    val searchText: String,
    val page: Int,
    val size: Int
)
