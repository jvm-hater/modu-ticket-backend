package com.jvmhater.moduticket.repository

import com.jvmhater.moduticket.model.Modu
import com.jvmhater.moduticket.model.ModuRow
import com.jvmhater.moduticket.model.toRow
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository

@Repository
class ModuRepositoryImpl(
    private val databaseClient: DatabaseClient,
    private val r2dbcEntityTemplate: R2dbcEntityTemplate,
    private val moduR2dbcRepository: ModuR2dbcRepository,
) : ModuRepository {

    override suspend fun save(modu: Modu): Modu {
        return moduR2dbcRepository.save(modu.toRow(isNewRow = true)).toDomain()
    }

    override suspend fun findById(id: String): Modu {
        return moduR2dbcRepository.findById(id)?.toDomain() ?: throw RuntimeException()
    }

    override suspend fun update(modu: Modu): Modu {
        return moduR2dbcRepository.save(modu.toRow()).toDomain()
    }
}

@Repository interface ModuR2dbcRepository : CoroutineCrudRepository<ModuRow, String> {}
