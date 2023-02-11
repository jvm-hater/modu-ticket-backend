package com.jvmhater.moduticket.repository

import com.jvmhater.moduticket.exception.RepositoryException
import com.jvmhater.moduticket.model.Coupon
import com.jvmhater.moduticket.model.CouponRow
import com.jvmhater.moduticket.model.IssuedCouponRow
import com.jvmhater.moduticket.model.toRow
import com.jvmhater.moduticket.util.ifNullThrow
import com.jvmhater.moduticket.util.unknownDbExceptionHandle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.dao.TransientDataAccessResourceException
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.r2dbc.UncategorizedR2dbcException
import org.springframework.stereotype.Repository

@Repository
class SpringDataCouponRepository(
    private val r2dbcCouponRepository: R2dbcCouponRepository,
    private val r2dbcIssuedCouponRepository: R2dbcIssuedCouponRepository,
) : CouponRepository {

    override suspend fun findCoupons(name: String): List<Coupon> = unknownDbExceptionHandle {
        r2dbcCouponRepository.findByName(name).map { it.toDomain() }.toList()
    }

    override suspend fun find(id: String): Coupon = unknownDbExceptionHandle {
        val record =
            r2dbcCouponRepository.findById(id)
                ?: throw RepositoryException.RecordNotFound(message = "존재하지 않은 쿠폰 ID 입니다.")
        record.toDomain()
    }

    override suspend fun create(coupon: Coupon): Coupon = unknownDbExceptionHandle {
        try {
            r2dbcCouponRepository.save(coupon.toRow(isNewRow = true)).toDomain()
        } catch (e: UncategorizedR2dbcException) {
            throw RepositoryException.RecordAlreadyExisted(e, "쿠폰 레코드가 이미 존재합니다.")
        }
    }

    override suspend fun update(coupon: Coupon): Coupon = unknownDbExceptionHandle {
        try {
            r2dbcCouponRepository.save(coupon.toRow()).toDomain()
        } catch (e: TransientDataAccessResourceException) {
            throw RepositoryException.RecordNotFound(e, "존재하지 않는 쿠폰 ID 입니다.")
        }
    }

    override suspend fun delete(id: String) = unknownDbExceptionHandle {
        r2dbcCouponRepository
            .findById(id)
            .ifNullThrow(
                ifNotNull = { it.toDomain() },
                exception = RepositoryException.RecordNotFound(message = "존재하지 않은 쿠폰 ID 입니다.")
            )
        r2dbcCouponRepository.deleteById(id)
        r2dbcIssuedCouponRepository.deleteAllByCouponId(id)
    }

    override suspend fun issue(userId: String, coupon: Coupon): Coupon = unknownDbExceptionHandle {
        val issuedCoupon = update(coupon.issue())
        r2dbcIssuedCouponRepository.save(
            IssuedCouponRow(isNewRow = true, userId = userId, couponId = issuedCoupon.id)
        )
        issuedCoupon
    }
}

@Repository
interface R2dbcCouponRepository : CoroutineCrudRepository<CouponRow, String> {
    fun findByName(name: String): Flow<CouponRow>

    @Query(
        """
           SELECT coupon.* FROM coupon 
           JOIN issued_coupon 
           ON coupon.id = issued_coupon.coupon_id 
           WHERE issued_coupon.user_id = :userId 
        """
    )
    fun findCouponJoinIssuedCouponByUserId(userId: String): Flow<CouponRow>
}

@Repository
interface R2dbcIssuedCouponRepository : CoroutineCrudRepository<IssuedCouponRow, String> {
    fun deleteAllByCouponId(couponId: String)
    fun deleteAllByUserId(userId: String)
}
