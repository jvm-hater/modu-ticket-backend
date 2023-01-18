package com.jvmhater.moduticket.configuration

import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryOptions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.r2dbc.connection.R2dbcTransactionManager
import org.springframework.transaction.ReactiveTransactionManager

@Configuration
@EnableR2dbcRepositories
class MySQLConfiguration : AbstractR2dbcConfiguration() {

    @Autowired private lateinit var env: Environment

    @Bean
    @Qualifier("mySQLConnectionFactory")
    override fun connectionFactory(): ConnectionFactory {
        return ConnectionFactories.get(
            ConnectionFactoryOptions.builder()
                .option(ConnectionFactoryOptions.DRIVER, "mysql")
                .option(
                    ConnectionFactoryOptions.HOST,
                    env.getProperty("CONF_MODU_TICKET_DB_HOST", "localhost")
                )
                .option(
                    ConnectionFactoryOptions.PORT,
                    env.getProperty("CONF_MODU_TICKET_DB_PORT", "3306").toInt()
                )
                .option(
                    ConnectionFactoryOptions.DATABASE,
                    env.getProperty("CONF_MODU_TICKET_DB_DATABASE", "modu_ticket")
                )
                .option(
                    ConnectionFactoryOptions.USER,
                    env.getProperty("CONF_MODU_TICKET_DB_USERNAME", "local")
                )
                .option(
                    ConnectionFactoryOptions.PASSWORD,
                    env.getProperty("CONF_MODU_TICKET_DB_PASSWORD", "local")
                )
                .build()
        )
    }

    @Bean
    fun transactionManager(
        @Qualifier("mySQLConnectionFactory") connectionFactory: ConnectionFactory
    ): ReactiveTransactionManager {
        return R2dbcTransactionManager(connectionFactory)
    }
}
