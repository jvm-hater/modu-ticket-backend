package com.jvmhater.moduticket.repository

import com.jvmhater.moduticket.TestContainerTest
import com.jvmhater.moduticket.genModu
import com.jvmhater.moduticket.readResourceFile
import com.jvmhater.moduticket.testcontainers.TestMySQLContainer
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.springframework.r2dbc.core.DatabaseClient

@TestContainerTest
class SpringDataModuRepositoryTest(
    databaseClient: DatabaseClient,
    moduR2dbcRepository: ModuR2dbcRepository,
) : DescribeSpec({ afterEach { TestMySQLContainer.sql(readResourceFile("ddl/truncate.sql")) } }) {

    private val moduRepositoryImpl = ModuRepositoryImpl(databaseClient, moduR2dbcRepository)

    init {
        describe("#save") {
            it("success") {
                val savedModu = moduRepositoryImpl.save(genModu())
                moduRepositoryImpl.findById(savedModu.id) shouldBe savedModu
            }
        }

        describe("#update") {
            it("success") {
                val savedModu = moduRepositoryImpl.save(genModu())
                moduRepositoryImpl.update(savedModu.copy(name = "update-name")) shouldBe
                    savedModu.copy(name = "update-name")
            }
        }
    }
}
