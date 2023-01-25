package com.jvmhater.moduticket.configuration

import org.flywaydb.core.Flyway
import org.springframework.boot.autoconfigure.flyway.FlywayProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

// https://stackoverflow.com/a/61412233
@Configuration
class FlywayConfiguration {

    @Bean
    fun flyway(flywayProperties: FlywayProperties): Flyway {
        val flyway =
            Flyway.configure()
                .baselineOnMigrate(flywayProperties.isBaselineOnMigrate)
                .baselineVersion(flywayProperties.baselineVersion)
                .dataSource(flywayProperties.url, flywayProperties.user, flywayProperties.password)
                .load()

        // mysql은 실패한 마이그레이션 이력을 repair()로 지울 수 있습니다.
        flyway.repair()
        flyway.migrate()
        return flyway
    }

    @Bean
    fun flywayProperties(): FlywayProperties {
        return FlywayProperties()
    }
}
