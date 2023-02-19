package com.jvmhater.moduticket

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication @ConfigurationPropertiesScan class ModuTicketBackendApplication

fun main(args: Array<String>) {
    runApplication<ModuTicketBackendApplication>(*args)
}
