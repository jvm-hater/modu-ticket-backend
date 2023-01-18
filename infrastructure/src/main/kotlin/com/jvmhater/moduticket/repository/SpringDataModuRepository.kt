package com.jvmhater.moduticket.repository

import com.jvmhater.moduticket.model.Modu
import com.jvmhater.moduticket.model.ModuEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository

@Repository
class ModuRepositoryImpl(
    private val databaseClient: DatabaseClient,
    private val moduR2dbcRepository: ModuR2dbcRepository
) : ModuRepository {

    override suspend fun save(modu: Modu): Modu {
        return moduR2dbcRepository.save(ModuEntity.toInsert(modu)).toDomain()
    }

    override suspend fun findById(id: String): Modu {
        return moduR2dbcRepository.findById(id)?.toDomain() ?: throw RuntimeException()
    }

    override suspend fun update(modu: Modu): Modu {
        return moduR2dbcRepository.save(ModuEntity.toUpdate(modu)).toDomain()
    }
}

@Repository
interface ModuR2dbcRepository : CoroutineCrudRepository<ModuEntity, String> {}
