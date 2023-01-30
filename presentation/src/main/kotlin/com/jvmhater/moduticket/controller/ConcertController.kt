package com.jvmhater.moduticket.controller

import com.jvmhater.moduticket.dto.response.ConcertResponse
import com.jvmhater.moduticket.dto.response.ConcertsResponse
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
    suspend fun viewConcerts(): ResponseEntity<ConcertsResponse> = handle { TODO() }

    @Operation(description = "콘서트 목록을 조회한다.")
    @GetMapping("/{concert_id}")
    suspend fun viewConcert(
        @PathVariable("concert_id") concertId: String
    ): ResponseEntity<ConcertResponse> = handle { TODO() }

    @Operation(description = "콘서트를 생성한다.")
    @PostMapping
    suspend fun createConcert(): ResponseEntity<ConcertResponse> = createHandle { TODO() }

    @Operation(description = "콘서트를 삭제한다.")
    @DeleteMapping
    suspend fun deleteConcert(): ResponseEntity<Unit> = deleteHandle { TODO() }
}
