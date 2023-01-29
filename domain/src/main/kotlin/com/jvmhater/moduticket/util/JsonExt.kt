package com.jvmhater.moduticket.util

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule

object JsonHelper {
    private val objectMapper =
        object : ThreadLocal<ObjectMapper>() {
            override fun initialValue(): ObjectMapper {
                return ObjectMapper().apply {
                    registerModule(JavaTimeModule())
                        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                        .registerModule(
                            KotlinModule.Builder()
                                .withReflectionCacheSize(512)
                                .configure(KotlinFeature.NullToEmptyCollection, false)
                                .configure(KotlinFeature.NullToEmptyMap, false)
                                .configure(KotlinFeature.NullIsSameAsDefault, false)
                                .configure(KotlinFeature.SingletonSupport, false)
                                .configure(KotlinFeature.StrictNullChecks, false)
                                .build()
                        )
                }
            }
        }

    fun get(): ObjectMapper {
        return objectMapper.get()
    }
}

fun Any.toJson(): String {
    return JsonHelper.get().writeValueAsString(this)
}

inline fun <reified T> String.toObject(): T {
    return JsonHelper.get().readValue(this, object : TypeReference<T>() {})
}
