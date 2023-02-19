package com.jvmhater.moduticket.service

import com.jvmhater.moduticket.JwtProvider
import com.jvmhater.moduticket.exception.DomainException
import com.jvmhater.moduticket.exception.RepositoryException
import com.jvmhater.moduticket.model.UserFixture
import com.jvmhater.moduticket.repository.TokenProvider
import com.jvmhater.moduticket.repository.UserRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk

class AuthServiceTest : DescribeSpec() {
    private val userRepository: UserRepository = mockk()
    private val tokenProvider: TokenProvider = mockk()
    private val authService = AuthService(userRepository, tokenProvider)

    init {
        describe("#login") {
            context("올바른 유저 정보가 들어왔을때") {
                coEvery { userRepository.find("test") } returns
                    UserFixture.generate(password = "password", id = "test")
                coEvery { tokenProvider.createToken("test") } returns "test-token"
                it("성공한다.") {
                    val result = authService.login("test", "password")
                    result shouldBe "test-token"
                }
            }
            context("해당 유저가 존재하지 않을 때") {
                coEvery { userRepository.find("test") } throws
                    RepositoryException.RecordNotFound(message = "존재하지 않는 유저입니다.")
                it("존재하지 않는 유저라는 오류가 발생한다.") {
                    shouldThrow<RepositoryException.RecordNotFound> {
                        authService.login("test", "password")
                    }
                }
            }
            context("잘못된 비밀번호를 입력했을 때") {
                coEvery { userRepository.find("test") } returns
                    UserFixture.generate(password = "password")
                it("비밀번호 오류가 발생한다.") {
                    shouldThrow<DomainException.InvalidArgumentException> {
                        authService.login("test", "password2")
                    }
                }
            }
        }
        describe("#signup") {
            context("When the request is valid") {
                coEvery { userRepository.create("test", "password") } returns Unit
                it("success") {
                    val result = authService.signUp("test", "password")
                    result shouldBe Unit
                }
            }
            context("When the user already exists") {
                coEvery { userRepository.create("test", "password") } throws
                    RepositoryException.RecordAlreadyExisted(message = "이미 존재하는 유저입니다.")
                it("throw RecordAlreadyExisted") {
                    shouldThrow<RepositoryException.RecordAlreadyExisted> {
                        authService.signUp("test", "password")
                    }
                }
            }
        }
    }
}
