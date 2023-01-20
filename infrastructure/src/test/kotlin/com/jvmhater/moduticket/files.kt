package com.jvmhater.moduticket

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.stream.Collectors
import org.springframework.core.io.ClassPathResource

fun readResourceFile(fileInJar: String): String {
    val resource = ClassPathResource(fileInJar)
    val inputStream = resource.inputStream

    return BufferedReader(InputStreamReader(inputStream)).use { bufferedReader: BufferedReader ->
        bufferedReader.lines().collect(Collectors.joining("\n"))
    }
}
