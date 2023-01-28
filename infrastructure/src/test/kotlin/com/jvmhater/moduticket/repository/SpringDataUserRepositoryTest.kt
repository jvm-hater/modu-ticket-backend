package com.jvmhater.moduticket.repository

import com.jvmhater.moduticket.TestContainerTest
import com.jvmhater.moduticket.exception.RepositoryException
import com.jvmhater.moduticket.model.Rank
import com.jvmhater.moduticket.model.User
import com.jvmhater.moduticket.testcontainers.TestMySQLContainer
import com.jvmhater.moduticket.util.readResourceFile
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.shouldBe

@TestContainerTest
class SpringDataUserRepositoryTest(
    r2dbcUserRepository: R2dbcUserRepository,
) : DescribeSpec() {

    override suspend fun afterEach(testCase: TestCase, result: TestResult) {
        TestMySQLContainer.sql(readResourceFile("ddl/truncate.sql"))
    }

    private val userRepository = SpringDataUserRepository(r2dbcUserRepository)

    init {
        describe("#create") {
            context("새로운 id의 유저라면") {
                userRepository.create(id = "test1", password = "testPassword")
                it("테이블에 값을 저장한다") { userRepository.find("test1").id shouldBe "test1" }
            }
            context("이미 있는 id의 유저라면") {
                userRepository.create(id = "test1", password = "testPassword")
                it("값 저장을 실패한다.") {
                    shouldThrow<RepositoryException.RecordAlreadyExisted> {
                        userRepository.create(id = "test1", password = "testPassword")
                    }
                }
            }
        }
        describe("#find") {
            context("테이블에 있는 유저라면") {
                userRepository.create(id = "test1", password = "testPassword")
                it("해당 유저를 리턴한다.") {
                    userRepository.find("test1") shouldBe
                        User(id = "test1", point = 0, rank = Rank.BRONZE)
                }
            }
            context("해당 유저가 존재하지 않는다면") {
                it("조회를 실패한다.") {
                    shouldThrow<RepositoryException.RecordNotFound> {
                        userRepository.find(id = "test3")
                    }
                }
            }
        }
        describe("#findAll") {
            context("테이블에 있는 유저라면") {
                userRepository.create(id = "test1", password = "testPassword")
                it("해당 유저를 삭제한다.") { userRepository.delete("test1") shouldBe Unit }
            }
            context("해당 유저가 존재하지 않는다면") {
                it("삭제를 실패한다.") {
                    shouldThrow<RepositoryException.RecordNotFound> {
                        userRepository.delete("test1")
                    }
                }
            }
        }
    }
}
