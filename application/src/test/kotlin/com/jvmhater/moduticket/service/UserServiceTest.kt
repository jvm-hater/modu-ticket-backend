package com.jvmhater.moduticket.service

import com.jvmhater.moduticket.exception.RepositoryException
import com.jvmhater.moduticket.model.UserFixture
import com.jvmhater.moduticket.repository.UserRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk

class UserServiceTest : DescribeSpec() {
    private val userRepository: UserRepository = mockk()
    private val userService = UserService(userRepository)

    init {
        describe("#find") {
            context("When the user exist") {
                val user = UserFixture.generate()
                coEvery { userRepository.find("test") } returns user
                it("success") {
                    val result = userService.find("test")
                    result shouldBe user
                }
            }
            context("When the user not exists") {
                coEvery { userRepository.find("test") } throws
                    RepositoryException.RecordNotFound(message = "존재하지 않는 유저입니다.")
                it("throw RecordNotFound") {
                    shouldThrow<RepositoryException.RecordNotFound> { userService.find("test") }
                }
            }
        }
        describe("#delete") {
            context("When the user exist") {
                coEvery { userRepository.delete("test") } returns Unit
                it("success") {
                    val result = userService.delete("test")
                    result shouldBe Unit
                }
            }
            context("When the user not exists") {
                coEvery { userRepository.delete("test") } throws
                    RepositoryException.RecordNotFound(message = "존재하지 않는 유저입니다.")
                it("throw RecordNotFound") {
                    shouldThrow<RepositoryException.RecordNotFound> { userService.delete("test") }
                }
            }
        }
    }
}
