package com.jvmhater.moduticket.repository

import com.jvmhater.moduticket.exception.RepositoryException
import com.jvmhater.moduticket.model.*
import com.jvmhater.moduticket.model.query.ConcertSearchQuery
import com.jvmhater.moduticket.model.query.Page
import kotlinx.coroutines.flow.toList
import org.springframework.dao.DataAccessException
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
        try {
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
                        .offset(page.page.toLong())
                )
                .flow()
                .toList()
                .toDomains()
        } catch (e: DataAccessException) {
            throw RepositoryException.UnknownAccessFailure(e)
        }

    override suspend fun find(id: String): Concert =
        try {
            val concertRow =
                r2dbcConcertRepository.findById(id)
                    ?: throw RepositoryException.RecordNotFound(message = "존재하지 않은 콘서트 ID 입니다.")
            concertRow.toDomain()
        } catch (e: DataAccessException) {
            throw RepositoryException.UnknownAccessFailure(e)
        }

    override suspend fun create(concert: Concert): Concert =
        try {
            val concertRow = r2dbcConcertRepository.save(concert.toRow(isNewRow = true)).toDomain()
            val seatRows =
                r2dbcSeatRepository
                    .saveAll(concert.seats.toRows(isNewRow = true, concertId = concertRow.id))
                    .toList()
                    .toDomains()

            concertRow.addSeats(seatRows)
        } catch (e: DataIntegrityViolationException) {
            throw RepositoryException.RecordAlreadyExisted(e, "콘서트 레코드가 이미 존재합니다.")
        } catch (e: DataAccessException) {
            throw RepositoryException.UnknownAccessFailure(e)
        }

    override suspend fun delete(id: String) =
        try {
            if (!r2dbcConcertRepository.existsById(id)) {
                throw RepositoryException.RecordNotFound(message = "존재하지 않는 콘서트 ID 입니다.")
            }

            r2dbcConcertRepository.deleteById(id)
            r2dbcSeatRepository.deleteByConcertId(id)
        } catch (e: DataAccessException) {
            throw RepositoryException.UnknownAccessFailure(e)
        }
}

@Repository
interface R2dbcConcertRepository : CoroutineCrudRepository<ConcertRow, String>
