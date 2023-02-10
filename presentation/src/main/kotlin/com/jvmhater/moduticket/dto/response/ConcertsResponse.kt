package com.jvmhater.moduticket.dto.response

import com.jvmhater.moduticket.model.Concert

data class ConcertsResponse(val concerts: List<ConcertResponse>)

fun List<Concert>.toResponse(): ConcertsResponse =
    ConcertsResponse(concerts = this.map { it.toResponse() })
