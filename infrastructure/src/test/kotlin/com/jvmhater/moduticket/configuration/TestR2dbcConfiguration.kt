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
        val property = TestMySQLContainer.start()

        return ConnectionFactories.get(
            ConnectionFactoryOptions.builder()
                .option(ConnectionFactoryOptions.DRIVER, "mysql")
                .option(ConnectionFactoryOptions.HOST, property.host)
                .option(ConnectionFactoryOptions.PORT, property.port)
                .option(ConnectionFactoryOptions.DATABASE, property.databaseName)
                .option(ConnectionFactoryOptions.USER, property.username)
                .option(ConnectionFactoryOptions.PASSWORD, property.password)
                .build()
        )
    }
}
