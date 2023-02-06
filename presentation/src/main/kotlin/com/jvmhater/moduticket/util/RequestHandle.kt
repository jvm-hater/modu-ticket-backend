package com.jvmhater.moduticket.util

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity

object handle {
    suspend inline operator fun <reified T> invoke(
        crossinline f: suspend () -> T
    ): ResponseEntity<T> {
        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(f())
    }
}

object createHandle {
    suspend inline operator fun <reified T> invoke(
        crossinline f: suspend () -> T
    ): ResponseEntity<T> {
        return ResponseEntity.status(HttpStatus.CREATED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(f())
    }
}

object deleteHandle {
    suspend inline operator fun invoke(crossinline f: suspend () -> Unit): ResponseEntity<Unit> {
        f()
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}
