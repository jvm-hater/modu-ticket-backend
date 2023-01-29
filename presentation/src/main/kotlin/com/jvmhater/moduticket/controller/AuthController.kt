package com.jvmhater.moduticket.controller

import com.jvmhater.moduticket.dto.request.LoginRequest
import com.jvmhater.moduticket.service.AuthService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class AuthController(val authService: AuthService) {

    @Operation(description = "로그인 한다.")
    @PostMapping("/login")
    suspend fun login(@RequestBody request: LoginRequest) {
        authService.login(id = request.id, password = request.password)
    }
}
