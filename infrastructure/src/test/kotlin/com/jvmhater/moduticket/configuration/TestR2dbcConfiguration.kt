package com.jvmhater.moduticket.configuration

import com.jvmhater.moduticket.testcontainers.TestMySQLContainer
import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryOptions
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@Configuration
@EnableR2dbcRepositories("com.jvmhater.moduticket")
class TestR2dbcConfiguration : AbstractR2dbcConfiguration() {

    @Bean
    override fun connectionFactory(): ConnectionFactory {
        val databaseProperty = TestMySQLContainer.start()

        return ConnectionFactories.get(
            ConnectionFactoryOptions.builder()
                .option(ConnectionFactoryOptions.DRIVER, "mysql")
                .option(ConnectionFactoryOptions.HOST, databaseProperty.host)
                .option(ConnectionFactoryOptions.PORT, databaseProperty.port)
                .option(ConnectionFactoryOptions.DATABASE, databaseProperty.databaseName)
                .option(ConnectionFactoryOptions.USER, databaseProperty.username)
                .option(ConnectionFactoryOptions.PASSWORD, databaseProperty.password)
                .build()
        )
    }
}
