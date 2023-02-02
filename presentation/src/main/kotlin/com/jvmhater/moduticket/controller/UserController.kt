package com.jvmhater.moduticket.controller

import com.jvmhater.moduticket.dto.request.SignUpRequest
import com.jvmhater.moduticket.dto.response.UserResponse
import com.jvmhater.moduticket.service.UserService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class UserController(val userService: UserService) {

    @Operation(description = "회원가입 한다.")
    @PostMapping("/signup")
    suspend fun signUp(@RequestBody request: SignUpRequest) {
        userService.signUp(id = request.id, password = request.password)
    }

    @Operation(description = "유저 정보를 id로 조회 한다.")
    @GetMapping("/users/{id}")
    suspend fun viewUser(@PathVariable("id") id: String): UserResponse {
        return UserResponse.from(userService.find(id = id))
    }

    @Operation(description = "모든 유저 정보를 조회 한다.")
    @GetMapping("/users")
    suspend fun viewUsers(): List<UserResponse> {
        return userService.findAll().map { UserResponse.from(it) }
    }

    @Operation(description = "해당 id의 유저를 삭제한다.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/users/{id}")
    suspend fun deleteUser(@PathVariable("id") id: String) {
        return userService.delete(id)
    }
}
