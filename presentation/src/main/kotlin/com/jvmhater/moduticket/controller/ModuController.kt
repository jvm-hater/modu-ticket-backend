package com.jvmhater.moduticket.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ModuController {

    @Operation(description = "티켓을 발급한다.")
    @GetMapping("/api/test")
    suspend fun test(): String = "test"
}
