package com.jvmhater.moduticket.util

inline fun <T> Boolean.then(f: () -> T): T? =
    when (this) {
        true -> f()
        false -> null
    }
