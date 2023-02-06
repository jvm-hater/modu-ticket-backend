package com.jvmhater.moduticket.repository

import com.jvmhater.moduticket.exception.RepositoryException
import com.jvmhater.moduticket.model.*
import com.jvmhater.moduticket.model.query.ConcertSearchQuery
import com.jvmhater.moduticket.model.query.Page
import com.jvmhater.moduticket.util.dbExceptionHandle
import kotlinx.coroutines.flow.toList
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.r2dbc.core.flow
import org.springframework.data.r2dbc.core.select
import org.springframework.data.relational.core.query.Criteria.where
import org.springframework.data.relational.core.query.Query.query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
class SpringDataConcertRepository(
    private val r2dbcConcertRepository: R2dbcConcertRepository,
    private val r2dbcSeatRepository: R2dbcSeatRepository,
    private val template: R2dbcEntityTemplate
) : ConcertRepository {
    override suspend fun findConcerts(query: ConcertSearchQuery, page: Page): List<Concert> =
        dbExceptionHandle {
            template
                .select<ConcertRow>()
                .matching(
                    query(
                            where("category")
                                .`is`(query.category)
                                .and("name")
                                .like("%${query.searchText}%")
                        )
                        .limit(page.size)
                        .offset(page.page)
                )
                .flow()
                .toList()
                .toDomains()
        }

    override suspend fun find(id: String): Concert = dbExceptionHandle {
        val concertRow =
            r2dbcConcertRepository.findById(id)
                ?: throw RepositoryException.RecordNotFound(message = "존재하지 않은 콘서트 ID 입니다.")
        val seats = r2dbcSeatRepository.findByConcertId(id).toList().toDomains()

        concertRow.toDomain().updateSeats(seats)
    }

    override suspend fun create(concert: Concert): Concert = dbExceptionHandle {
        try {
            val savedConcert =
                r2dbcConcertRepository.save(concert.toRow(isNewRow = true)).toDomain()
            val savedSeats =
                r2dbcSeatRepository
                    .saveAll(concert.seats.toRows(isNewRow = true, concertId = savedConcert.id))
                    .toList()
                    .toDomains()

            savedConcert.updateSeats(savedSeats)
        } catch (e: DataIntegrityViolationException) {
            throw RepositoryException.RecordAlreadyExisted(e, "콘서트 레코드가 이미 존재합니다.")
        }
    }

    override suspend fun delete(id: String) = dbExceptionHandle {
        r2dbcConcertRepository.deleteById(id)
        r2dbcSeatRepository.deleteByConcertId(id)
    }
}

@Repository interface R2dbcConcertRepository : CoroutineCrudRepository<ConcertRow, String>
