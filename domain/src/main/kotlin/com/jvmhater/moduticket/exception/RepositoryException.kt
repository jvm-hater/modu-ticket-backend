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
        override val message: String = "데이터베이스 연결에 실패하였습니다.",
    ) : RepositoryException(message, cause)

    data class DataIntegrityException(
        override val cause: Throwable? = null,
        override val message: String,
    ) : RepositoryException(message, cause)
}
