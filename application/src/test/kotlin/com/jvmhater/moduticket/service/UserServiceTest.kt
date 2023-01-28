package com.jvmhater.moduticket.service

import com.jvmhater.moduticket.exception.DomainException
import com.jvmhater.moduticket.exception.RepositoryException
import com.jvmhater.moduticket.model.Rank
import com.jvmhater.moduticket.model.User
import com.jvmhater.moduticket.model.UserFixture
import com.jvmhater.moduticket.repository.AuthRepository
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
        describe("#signup") {
            context("When the request is valid") {
                coEvery { userRepository.create("test", "password") } returns Unit
                it("success") {
                    val result = userService.signUp("test", "password")
                    result shouldBe Unit
                }
            }
            context("When the user already exists") {
                coEvery { userRepository.create("test", "password") } throws RepositoryException.RecordAlreadyExisted(
                    message = "이미 존재하는 유저입니다."
                )
                it("throw RecordAlreadyExisted") {
                    shouldThrow<RepositoryException.RecordAlreadyExisted> {
                        userService.signUp("test", "password")
                    }
                }
            }
        }
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
                coEvery { userRepository.find("test") } throws RepositoryException.RecordNotFound(message = "존재하지 않는 유저입니다.")
                it("throw RecordNotFound") {
                    shouldThrow<RepositoryException.RecordNotFound> {
                        userService.find("test")
                    }
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
                coEvery { userRepository.delete("test") } throws RepositoryException.RecordNotFound(message = "존재하지 않는 유저입니다.")
                it("throw RecordNotFound") {
                    shouldThrow<RepositoryException.RecordNotFound> {
                        userService.delete("test")
                    }
                }
            }
        }
    }
}