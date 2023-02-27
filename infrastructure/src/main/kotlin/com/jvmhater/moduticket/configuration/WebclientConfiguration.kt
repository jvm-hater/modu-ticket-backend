package com.jvmhater.moduticket.configuration

import com.jvmhater.moduticket.client.generateWebclient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebclientConfiguration {

    @Bean
    fun portoneWebclient(@Value("\${portone.baseurl}") baseUrl: String): WebClient =
        generateWebclient(baseUrl)
}
