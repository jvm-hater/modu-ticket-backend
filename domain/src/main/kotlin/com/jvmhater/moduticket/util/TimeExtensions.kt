package com.jvmhater.moduticket.util

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun LocalDateTime.toInstant(): Instant = this.atZone(getKoreaZoneId()).toInstant()

fun Instant.toLocalDateTime(): LocalDateTime = LocalDateTime.ofInstant(this, getKoreaZoneId())

fun getKoreaZoneId(): ZoneId = ZoneId.of("Asia/Seoul")
