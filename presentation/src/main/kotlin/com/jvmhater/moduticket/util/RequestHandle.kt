package com.jvmhater.moduticket.util

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity

suspend inline fun <reified T> handle(crossinline f: suspend () -> T): ResponseEntity<T> {
    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(f())
}

suspend inline fun <reified T> createHandle(crossinline f: suspend () -> T): ResponseEntity<T> {
    return ResponseEntity.status(HttpStatus.CREATED)
        .contentType(MediaType.APPLICATION_JSON)
        .body(f())
}

suspend inline fun deleteHandle(crossinline f: suspend () -> Unit): ResponseEntity<Unit> {
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
}
