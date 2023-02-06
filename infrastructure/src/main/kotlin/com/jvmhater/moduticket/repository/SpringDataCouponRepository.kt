package com.jvmhater.moduticket.repository

import com.jvmhater.moduticket.exception.RepositoryException
import com.jvmhater.moduticket.model.Coupon
import com.jvmhater.moduticket.model.CouponRow
import com.jvmhater.moduticket.model.IssuedCouponRow
import com.jvmhater.moduticket.model.toRow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.dao.DataAccessException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.TransientDataAccessResourceException
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
class SpringDataCouponRepository(
    private val r2dbcCouponRepository: R2dbcCouponRepository,
    private val r2dbcIssuedCouponRepository: R2dbcIssuedCouponRepository,
) : CouponRepository {

    override suspend fun findCoupons(name: String): List<Coupon> {
        try {
            return r2dbcCouponRepository.findByName(name).map { it.toDomain() }.toList()
        } catch (e: DataAccessException) {
            throw RepositoryException.UnknownAccessFailure(e, "데이터베이스 연결에 실패하였습니다.")
        }
    }

    override suspend fun find(id: String): Coupon {
        try {
            val record =
                r2dbcCouponRepository.findById(id)
                    ?: throw RepositoryException.RecordNotFound(message = "존재하지 않은 쿠폰 ID 입니다.")
            return record.toDomain()
        } catch (e: DataAccessException) {
            throw RepositoryException.UnknownAccessFailure(e, "데이터베이스 연결에 실패하였습니다.")
        }
    }

    override suspend fun create(coupon: Coupon): Coupon {
        try {
            return r2dbcCouponRepository.save(coupon.toRow(isNewRow = true)).toDomain()
        } catch (e: DataIntegrityViolationException) {
            throw RepositoryException.RecordAlreadyExisted(e, "쿠폰 레코드가 이미 존재합니다.")
        } catch (e: DataAccessException) {
            throw RepositoryException.UnknownAccessFailure(e, "데이터베이스 연결에 실패하였습니다.")
        }
    }

    override suspend fun update(coupon: Coupon): Coupon {
        try {
            return r2dbcCouponRepository.save(coupon.toRow()).toDomain()
        } catch (e: TransientDataAccessResourceException) {
            throw RepositoryException.RecordNotFound(e, "존재하지 않는 쿠폰 ID 입니다.")
        } catch (e: DataAccessException) {
            throw RepositoryException.UnknownAccessFailure(e, "데이터베이스 연결에 실패하였습니다.")
        }
    }

    override suspend fun delete(id: String) {
        r2dbcCouponRepository.findById(id)
            ?: throw RepositoryException.RecordNotFound(message = "존재하지 않는 쿠폰 ID 입니다.")
        r2dbcCouponRepository.deleteById(id)
    }

    override suspend fun issue(userId: String, coupon: Coupon): Coupon {
        val issuedCoupon = update(coupon.copy(issuableQuantity = coupon.issuableQuantity - 1))
        r2dbcIssuedCouponRepository.save(
            IssuedCouponRow(isNewRow = true, userId = userId, couponId = issuedCoupon.id)
        )
        return issuedCoupon
    }
}

@Repository
interface R2dbcCouponRepository : CoroutineCrudRepository<CouponRow, String> {
    fun findByName(name: String): Flow<CouponRow>

    @Query(
        "SELECT coupon.* FROM coupon INNER JOIN issued_coupon ON coupon.id = issued_coupon.coupon_id WHERE issued_coupon.user_id = :userId"
    )
    fun findCouponInnerJoinIssuedCouponByUserId(userId: String): Flow<CouponRow>
}

@Repository
interface R2dbcIssuedCouponRepository : CoroutineCrudRepository<IssuedCouponRow, String>
