package com.jvmhater.moduticket

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

@Repository
class ModuRepositoryImpl(
    private val moduQueryService: ModuQueryService,
    private val moduR2dbcRepository: ModuR2dbcRepository
) : ModuRepository {
    /* combination */
    override fun comb(): Modu {
        // val id = moduQueryService.query()
        // moduR2dbcRepository.findAllById(id)
        return Modu()
    }
}

interface ModuR2dbcRepository : ReactiveCrudRepository<ModuEntity, String> {
    /* method query */
}

@Service
class ModuQueryService() {
    /* native query */
    fun query(): Long = 1L
}
