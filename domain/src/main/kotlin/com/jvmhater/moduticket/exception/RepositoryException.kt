package com.jvmhater.moduticket.exception

sealed class RepositoryException(
    override val message: String,
    override val cause: Throwable? = null
) : RuntimeException(message, cause) {

    data class RecordNotFound(override val cause: Throwable? = null, override val message: String) :
        RepositoryException(message, cause)

    data class RecordAlreadyExisted(
        override val cause: Throwable? = null,
        override val message: String,
    ) : RepositoryException(message, cause)

    data class UnknownAccessFailure(
        override val cause: Throwable? = null,
        override val message: String,
    ) : RepositoryException(message, cause)

    data class DataIntegrityException(
        override val cause: Throwable? = null,
        override val message: String,
    ) : RepositoryException(message, cause)
}
