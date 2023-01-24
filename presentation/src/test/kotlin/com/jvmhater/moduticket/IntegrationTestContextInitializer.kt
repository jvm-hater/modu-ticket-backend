package com.jvmhater.moduticket

import com.jvmhater.moduticket.testcontainers.TestMySQLContainer
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext

/*
통합 테스트(integration test) 실행 전에 테스트 컨테이너를 실행시키도록 합니다.
https://www.baeldung.com/spring-boot-testcontainers-integration-test
*/
class IntegrationTestContextInitializer :
    ApplicationContextInitializer<ConfigurableApplicationContext> {
    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        TestMySQLContainer.start()
    }
}
