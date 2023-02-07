package com.jvmhater.moduticket.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.jvmhater.moduticket.dto.request.CreateConcertRequest
import com.jvmhater.moduticket.dto.request.ViewConcertsRequest
import com.jvmhater.moduticket.dto.response.ConcertResponse
import com.jvmhater.moduticket.dto.response.ConcertsResponse
import com.jvmhater.moduticket.dto.response.toResponse
import com.jvmhater.moduticket.model.query.ConcertSearchQuery
import com.jvmhater.moduticket.model.query.Page
import com.jvmhater.moduticket.service.ConcertService
import com.jvmhater.moduticket.util.createHandle
import com.jvmhater.moduticket.util.deleteHandle
import com.jvmhater.moduticket.util.handle
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/concerts")
class ConcertController(private val concertService: ConcertService) {

    @Operation(description = "콘서트 목록을 조회한다.")
    @GetMapping
    suspend fun viewConcerts(
        @RequestParam params: Map<String, String>
    ): ResponseEntity<ConcertsResponse> = handle {
        // TODO : query params refactoring
        val request = ObjectMapper().convertValue(params, ViewConcertsRequest::class.java)

        val query = ConcertSearchQuery(request.category, request.searchText).apply {}
        val page = Page(request.page, request.size)

        concertService.findConcerts(query, page).toResponse()
    }

    @Operation(description = "콘서트를 조회한다.")
    @GetMapping("/{concert_id}")
    suspend fun viewConcert(
        @PathVariable("concert_id") concertId: String
    ): ResponseEntity<ConcertResponse> = handle { concertService.find(concertId).toResponse() }

    @Operation(description = "콘서트를 생성한다.")
    @PostMapping
    suspend fun createConcert(
        @RequestBody request: CreateConcertRequest,
    ): ResponseEntity<ConcertResponse> = createHandle {
        val concert = request.toDomain()
        concertService.create(concert).toResponse()
    }

    @Operation(description = "콘서트를 삭제한다.")
    @DeleteMapping("/{concert_id}")
    suspend fun deleteConcert(@PathVariable("concert_id") concertId: String) = deleteHandle {
        concertService.delete(concertId)
    }
}
