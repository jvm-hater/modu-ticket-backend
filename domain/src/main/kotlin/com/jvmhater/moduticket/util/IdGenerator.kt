package com.jvmhater.moduticket.util

import java.util.UUID

fun generateId(prefix: String): String = prefix + UUID.randomUUID().toString()

fun generateId(): String = UUID.randomUUID().toString()
