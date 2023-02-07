package com.jvmhater.moduticket.util

@Suppress("UNCHECKED_CAST")
inline fun <R, T> T?.ifNullThrow(exception: Throwable, ifNotNull: (value: T) -> R): R =
    when (this) {
        null -> throw exception
        else -> ifNotNull(this as T)
    }
