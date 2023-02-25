package com.jvmhater.moduticket.repository

interface TokenProvider {
    fun createToken(id: String): String
}
