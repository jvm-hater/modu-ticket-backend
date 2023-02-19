package com.jvmhater.moduticket

import com.jvmhater.moduticket.configuration.TestElasticsearchConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [TestElasticsearchConfiguration::class])
annotation class TestElasticsearchContainerTest
