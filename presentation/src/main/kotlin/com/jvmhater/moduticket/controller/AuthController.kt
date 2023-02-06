package com.jvmhater.moduticket.controller

import com.jvmhater.moduticket.dto.request.LoginRequest
import com.jvmhater.moduticket.dto.request.SignUpRequest
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

    @Operation(description = "회원가입 한다.")
    @PostMapping("/signup")
    suspend fun signUp(@RequestBody request: SignUpRequest) {
        authService.signUp(id = request.id, password = request.password)
    }

}
