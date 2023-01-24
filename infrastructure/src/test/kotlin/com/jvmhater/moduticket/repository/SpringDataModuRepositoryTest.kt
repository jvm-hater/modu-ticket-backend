package com.jvmhater.moduticket.repository

import com.jvmhater.moduticket.model.Modu
import com.jvmhater.moduticket.readResourceFile
import com.jvmhater.moduticket.testcontainers.TestMySQLContainer
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.r2dbc.core.ReactiveSelectOperation.SelectWithQuery
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestR2bcConfiguration::class])
class SpringDataModuRepositoryTest(
    databaseClient: DatabaseClient,
    r2dbcEntityTemplate: R2dbcEntityTemplate,
    moduR2dbcRepository: ModuR2dbcRepository,
) : DescribeSpec({ afterEach { TestMySQLContainer.sql(readResourceFile("ddl/truncate.sql")) } }) {

    private val moduRepositoryImpl = ModuRepositoryImpl(databaseClient, r2dbcEntityTemplate, moduR2dbcRepository)

    init {
        describe("#save") {
            it("success") {
                val savedModu = moduRepositoryImpl.save(Modu(id = "id", name = "name"))
                moduRepositoryImpl.findById(savedModu.id) shouldBe savedModu
            }
        }

        describe("#update") {
            it("success") {
                val savedModu = moduRepositoryImpl.save(Modu(id = "id", name = "name"))
                moduRepositoryImpl.update(savedModu.copy(name = "update-name")) shouldBe
                    savedModu.copy(name = "update-name")
            }
        }
    }
}
