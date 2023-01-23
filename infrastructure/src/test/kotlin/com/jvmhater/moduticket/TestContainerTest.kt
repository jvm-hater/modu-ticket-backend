package com.jvmhater.moduticket

import com.jvmhater.moduticket.configuration.TestR2dbcConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestR2dbcConfiguration::class]) annotation class TestContainerTest
