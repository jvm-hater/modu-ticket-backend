package com.jvmhater.moduticket.repository

import com.jvmhater.moduticket.model.Coupon
import com.jvmhater.moduticket.model.CouponRow
import com.jvmhater.moduticket.model.toRow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
class SpringDataCouponRepository(
    private val r2dbcCouponRepository: R2dbcCouponRepository
) : CouponRepository {

    override suspend fun findCoupons(name: String): List<Coupon> {
        return r2dbcCouponRepository.findByName(name).map { it.toDomain() }.toList()
    }

    override suspend fun find(id: String): Coupon {
        return r2dbcCouponRepository.findById(id)?.toDomain() ?: throw RuntimeException()
    }

    override suspend fun create(coupon: Coupon): Coupon {
        return r2dbcCouponRepository.save(coupon.toRow(isNewRow = true)).toDomain()
    }

    override suspend fun update(coupon: Coupon): Coupon {
        return r2dbcCouponRepository.save(coupon.toRow()).toDomain()
    }

    override suspend fun delete(id: String) {
        r2dbcCouponRepository.deleteById(id)
    }
}

@Repository
interface R2dbcCouponRepository : CoroutineCrudRepository<CouponRow, String> {
    fun findByName(name: String): Flow<CouponRow>
}