package com.jvmhater.moduticket.util

import com.jvmhater.moduticket.exception.RepositoryException
import org.springframework.dao.DataAccessException

suspend inline fun <reified T> unknownDbExceptionHandle(crossinline block: suspend () -> T): T =
    try {
        block()
    } catch (e: DataAccessException) {
        throw RepositoryException.UnknownAccessFailure(e, "데이터베이스 연결에 실패하였습니다.")
    }

@Suppress("UNCHECKED_CAST")
inline fun <R, T> T?.ifNullThrow(exception: Throwable, ifNotNull: (value: T) -> R): R =
    when (this) {
        null -> throw exception
        else -> ifNotNull(this as T)
    }
