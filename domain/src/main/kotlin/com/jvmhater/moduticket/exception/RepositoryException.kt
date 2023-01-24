package com.jvmhater.moduticket.exception

sealed class RepositoryError(override val message: String, override val cause: Throwable? = null) :
    RuntimeException(message, cause) {
    val name: String = this.javaClass.simpleName ?: "UnknownException"
}

data class RecordNotFound(override val message: String, override val cause: Throwable? = null) :
    RepositoryError(message, cause)

data class RecordAlreadyExisted(
    override val cause: Throwable? = null,
    override val message: String
) : RepositoryError(message, cause)

data class UnknownAccessFailure(
    override val cause: Throwable? = null,
    override val message: String
) : RepositoryError(message, cause)
