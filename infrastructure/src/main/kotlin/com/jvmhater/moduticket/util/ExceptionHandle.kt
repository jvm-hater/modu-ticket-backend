package com.jvmhater.moduticket.util

import com.jvmhater.moduticket.exception.RepositoryException
import org.springframework.dao.DataAccessException

suspend inline fun <reified T> dbExceptionHandle(crossinline block: suspend () -> T): T =
    try {
        block()
    } catch (e: DataAccessException) {
        throw RepositoryException.UnknownAccessFailure(e)
    }
