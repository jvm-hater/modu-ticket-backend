package com.jvmhater.moduticket.exception

sealed class DomainException(override val message: String?, override val cause: Throwable? = null) :
    RuntimeException(message, cause) {

    data class InvalidRequestException(override val message: String, override val cause: Throwable? = null) :
        DomainException(message, cause)
}