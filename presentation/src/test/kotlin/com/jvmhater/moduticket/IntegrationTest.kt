package com.jvmhater.moduticket

import com.jvmhater.moduticket.configuration.MockBeanConfiguration
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
@MockBeanConfiguration
@AutoConfigureWebTestClient
@ContextConfiguration(initializers = [IntegrationTestContextInitializer::class])
annotation class IntegrationTest
