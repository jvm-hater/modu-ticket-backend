package com.jvmhater.moduticket.util

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity

suspend inline fun <reified T> handle(crossinline block: suspend () -> T): ResponseEntity<T> {
    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(block())
}

suspend inline fun <reified T> createHandle(crossinline block: suspend () -> T): ResponseEntity<T> {
    return ResponseEntity.status(HttpStatus.CREATED)
        .contentType(MediaType.APPLICATION_JSON)
        .body(block())
}

suspend inline fun deleteHandle(crossinline block: suspend () -> Unit): ResponseEntity<Unit> {
    block()
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
}
