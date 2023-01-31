package com.jvmhater.moduticket.controller

import com.jvmhater.moduticket.dto.request.CreateCouponRequest
import com.jvmhater.moduticket.dto.response.ConcertResponse
import com.jvmhater.moduticket.dto.response.ConcertsResponse
import com.jvmhater.moduticket.model.vo.ConcertCategory
import com.jvmhater.moduticket.util.createHandle
import com.jvmhater.moduticket.util.deleteHandle
import com.jvmhater.moduticket.util.handle
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/concerts")
class ConcertController {

    @Operation(description = "콘서트를 조회한다.")
    @GetMapping
    suspend fun viewConcerts(
        @RequestParam("category") category: ConcertCategory,
        @RequestParam("search_text") searchText: String
    ): ResponseEntity<ConcertsResponse> = handle { TODO() }

    @Operation(description = "콘서트 목록을 조회한다.")
    @GetMapping("/{concert_id}")
    suspend fun viewConcert(
        @PathVariable("concert_id") concertId: String
    ): ResponseEntity<ConcertResponse> = handle { TODO() }

    @Operation(description = "콘서트를 생성한다.")
    @PostMapping
    suspend fun createConcert(
        @RequestBody request: CreateCouponRequest,
    ): ResponseEntity<ConcertResponse> = createHandle { TODO() }

    @Operation(description = "콘서트를 삭제한다.")
    @DeleteMapping("/{concert_id}")
    suspend fun deleteConcert(@PathVariable("concert_id") concertId: String): ResponseEntity<Unit> =
        deleteHandle {
            TODO()
        }
}
