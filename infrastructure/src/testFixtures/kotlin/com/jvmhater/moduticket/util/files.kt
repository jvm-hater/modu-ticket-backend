package com.jvmhater.moduticket.util

import java.io.BufferedReader
import java.io.InputStreamReader
import org.springframework.core.io.ClassPathResource

fun readResourceFile(fileInJar: String): List<String> {
    val resource = ClassPathResource(fileInJar)
    val inputStream = resource.inputStream

    return BufferedReader(InputStreamReader(inputStream)).use { bufferedReader: BufferedReader ->
        bufferedReader.lines().toList()
    }
}
