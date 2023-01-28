package com.jvmhater.moduticket.repository

import com.jvmhater.moduticket.exception.DomainException
import com.jvmhater.moduticket.exception.RepositoryException
import org.springframework.stereotype.Repository

@Repository
class SpringDataAuthRepository(private val r2dbcUserRepository: R2dbcUserRepository) :
    AuthRepository {

    override suspend fun login(id: String, password: String) {
        val user =
            r2dbcUserRepository.findById(id)
                ?: throw RepositoryException.RecordNotFound(message = "존재하지 않는 유저입니다.")
        if (user.password != password) {
            throw DomainException.InvalidArgumentException(message = "비밀번호가 일치하지 않습니다.")
        }
    }
}
