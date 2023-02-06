package com.jvmhater.moduticket.exception

import com.jvmhater.moduticket.dto.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(value = [RepositoryException::class])
    fun handleRepositoryException(e: RepositoryException): ResponseEntity<ErrorResponse> {
        return when (e) {
            is RepositoryException.RecordNotFound -> ResponseEntity.status(HttpStatus.NOT_FOUND)
            is RepositoryException.RecordAlreadyExisted ->
                ResponseEntity.status(HttpStatus.BAD_REQUEST)
            is RepositoryException.UnknownAccessFailure ->
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            is RepositoryException.DataIntegrityException ->
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        }.body(ErrorResponse(e.message))
    }
    @ExceptionHandler(value = [DomainException::class])
    fun handleDomainException(e: DomainException): ResponseEntity<ErrorResponse> {
        return when (e) {
            is DomainException.InvalidArgumentException ->
                ResponseEntity.status(HttpStatus.BAD_REQUEST)
            is DomainException.UnauthorizedRequestException ->
                ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        }.body(ErrorResponse(e.message ?: ""))
    }
}
