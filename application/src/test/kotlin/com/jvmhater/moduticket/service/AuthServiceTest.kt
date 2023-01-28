package com.jvmhater.moduticket.service

import com.jvmhater.moduticket.exception.DomainException
import com.jvmhater.moduticket.exception.RepositoryException
import com.jvmhater.moduticket.repository.AuthRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk

class AuthServiceTest : DescribeSpec() {
    private val authRepository: AuthRepository = mockk()
    private val authService = AuthService(authRepository)
    init {
        describe("#login") {
            context("When the request is valid") {
                coEvery { authRepository.login("test", "password") } returns Unit
                it("success") {
                    val result = authService.login("test", "password")
                    result shouldBe Unit
                }
            }
            context("When the user not exists") {
                coEvery { authRepository.login("test", "password") } throws
                    RepositoryException.RecordNotFound(message = "존재하지 않는 유저입니다.")
                it("success") {
                    shouldThrow<RepositoryException.RecordNotFound> {
                        authService.login("test", "password")
                    }
                }
            }
            context("When the password is wrong") {
                coEvery { authRepository.login("test", "password") } throws
                    DomainException.InvalidArgumentException(message = "비밀번호가 일치하지 않습니다.")
                it("success") {
                    shouldThrow<DomainException.InvalidArgumentException> {
                        authService.login("test", "password")
                    }
                }
            }
        }
    }
}
