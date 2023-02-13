package com.jvmhater.moduticket.repository

import com.jvmhater.moduticket.exception.RepositoryException
import com.jvmhater.moduticket.model.User
import com.jvmhater.moduticket.model.UserRow
import com.jvmhater.moduticket.model.toDomains
import kotlinx.coroutines.flow.toList
import org.springframework.dao.DataAccessException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
class SpringDataUserRepository(
    private val r2dbcUserRepository: R2dbcUserRepository,
    private val r2dbcCouponRepository: R2dbcCouponRepository,
    private val r2dbcIssuedCouponRepository: R2dbcIssuedCouponRepository
) : UserRepository {

    override suspend fun create(id: String, password: String) {
        try {
            r2dbcUserRepository.save(UserRow(isNewRow = true, userId = id, password = password))
        } catch (e: DataIntegrityViolationException) {
            throw RepositoryException.RecordAlreadyExisted(e, "이미 존재하는 유저입니다.")
        } catch (e: DataAccessException) {
            throw RepositoryException.UnknownAccessFailure(e, "데이터베이스 연결에 실패하였습니다.")
        }
    }

    override suspend fun find(id: String): User {
        try {
            return r2dbcUserRepository.findByUserId(id).toDomain()
        } catch (e: Exception) {
            throw RepositoryException.RecordNotFound(message = "존재하지 않는 유저입니다.")
        }
    }

    override suspend fun findAll(): List<User> {
        return r2dbcUserRepository.findAll().toList().map { it.toDomain() }
    }

    override suspend fun findWithIssuedCoupon(id: String): User {
        try {
            val user = find(id)
            val coupons =
                r2dbcCouponRepository.findCouponJoinIssuedCouponByUserId(id).toList().toDomains()

            user.addCoupons(coupons)
            return user
        } catch (e: DataAccessException) {
            throw RepositoryException.UnknownAccessFailure(e, "데이터베이스 연결에 실패하였습니다.")
        }
    }

    override suspend fun delete(id: String) {
        r2dbcUserRepository.findById(id)
            ?: throw RepositoryException.RecordNotFound(message = "존재하지 않는 유저입니다.")
        r2dbcUserRepository.deleteById(id)
        r2dbcIssuedCouponRepository.deleteAllByUserId(id)
    }
}

@Repository
interface R2dbcUserRepository : CoroutineCrudRepository<UserRow, String> {
    suspend fun findByUserId(userId: String): UserRow
}
