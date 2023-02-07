package com.jvmhater.moduticket.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.jvmhater.moduticket.model.vo.ConcertCategory
import java.io.Serializable

data class ViewConcertsRequest(
    @JsonProperty("category") val category: ConcertCategory,
    @JsonProperty("search_text") val searchText: String,
    @JsonProperty("page") val page: Long,
    @JsonProperty("size") val size: Int
) : Serializable
