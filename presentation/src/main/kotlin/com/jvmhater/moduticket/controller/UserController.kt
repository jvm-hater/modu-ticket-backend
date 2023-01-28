package com.jvmhater.moduticket.controller

import com.jvmhater.moduticket.dto.request.AuthRequest
import com.jvmhater.moduticket.model.User
import com.jvmhater.moduticket.service.AuthService
import com.jvmhater.moduticket.service.UserService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class UserController(val userService: UserService) {

    @Operation(description = "회원가입 한다.")
    @PostMapping("/signup")
    suspend fun signUp(@RequestBody request: AuthRequest) {
        userService.signUp(id = request.id, password = request.password)
    }

    @Operation(description = "유저 정보를 id로 조회 한다.")
    @GetMapping("/users/{id}")
    suspend fun getUser(
        @PathVariable("id") id: String): User = userService.find(id = id)

    @Operation(description = "모든 유저 정보를 조회 한다.")
    @GetMapping("/users")
    suspend fun getUsers(): List<User> = userService.findAll()

    @Operation(description = "해당 id의 유저를 삭제한다.")
    @DeleteMapping("/users/{id}")
    suspend fun deleteUser(@PathVariable("id") id: String) = userService.delete(id)

}
