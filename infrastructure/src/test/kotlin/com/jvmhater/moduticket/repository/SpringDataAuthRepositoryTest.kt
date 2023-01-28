package com.jvmhater.moduticket.repository

import com.jvmhater.moduticket.TestContainerTest
import com.jvmhater.moduticket.exception.RepositoryException
import com.jvmhater.moduticket.model.CouponFixture
import com.jvmhater.moduticket.model.UserFixture
import com.jvmhater.moduticket.model.UserRow
import com.jvmhater.moduticket.testcontainers.TestMySQLContainer
import com.jvmhater.moduticket.util.generateId
import com.jvmhater.moduticket.util.readResourceFile
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.extensions.time.ConstantNowTestListener
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

@TestContainerTest
class SpringDataAuthRepositoryTest(
    r2dbcUserRepository: R2dbcUserRepository,
) : DescribeSpec() {


    override suspend fun afterEach(testCase: TestCase, result: TestResult) {
        TestMySQLContainer.sql(readResourceFile("ddl/truncate.sql"))
    }

    private val authRepository = SpringDataAuthRepository(r2dbcUserRepository)

    init {
        describe("#login"){
            context("when the user exists"){
                r2dbcUserRepository.save(UserRow(rowId = "infraTest", isNewRow = true, password = "test", point = 1234, rank = "bronze"))
                it("success") {
                    authRepository.login(id = "infraTest", password = "test") shouldBe Unit
                }
            }
            context("when the user exists"){
                r2dbcUserRepository.save(UserRow(rowId = "infra3Test", isNewRow = true, password = "test", point = 1234, rank = "bronze"))
                it("success") {
                    shouldThrow<RepositoryException.RecordNotFound> {
                        authRepository.login(id = "infraTes2t", password = "test") shouldBe Unit
                    }
                }
            }
        }
    }
}
