package com.jvmhater.moduticket.testcontainers

import dev.miku.r2dbc.mysql.MySqlConnectionFactoryProvider
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryOptions
import java.time.Duration
import org.flywaydb.core.Flyway
import org.springframework.r2dbc.core.DatabaseClient
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.containers.MySQLR2DBCDatabaseContainer
import org.testcontainers.containers.output.Slf4jLogConsumer

class TestMySQLContainer : MySQLContainer<TestMySQLContainer>("mysql:latest") {

    companion object {
        private const val USER_NAME = "test"
        private const val PASSWORD = "test"
        private const val DATABASE_NAME = "modu_ticket"

        private val kotlinLogger = mu.KotlinLogging.logger {}

        private lateinit var instance: TestMySQLContainer
        private lateinit var databaseClient: DatabaseClient
        private lateinit var connectionFactory: ConnectionFactory
        private lateinit var flyway: Flyway

        init {
            start()
        }

        private fun getConnectionFactoryOption(): ConnectionFactoryOptions {
            return MySQLR2DBCDatabaseContainer.getOptions(instance)
        }

        fun sql(sql: String) {
            databaseClient.sql(sql).then().block()
        }

        fun start(): ConnectionFactory {
            println("====Jayon-Test====")
            if (!Companion::instance.isInitialized) {
                instance =
                    TestMySQLContainer()
                        .withLogConsumer(Slf4jLogConsumer(kotlinLogger))
                        .withUsername(USER_NAME)
                        .withPassword(PASSWORD)
                        .withStartupTimeout(Duration.ofSeconds(60))
                        .withDatabaseName(DATABASE_NAME)
                        .apply { start() }
            }
            connectionFactory =
                MySqlConnectionFactoryProvider().create(getConnectionFactoryOption())
            databaseClient = DatabaseClient.create(connectionFactory)

            val port = instance.getMappedPort(MYSQL_PORT)
            flyway =
                Flyway.configure()
                    .baselineOnMigrate(true)
                    .baselineVersion("1")
                    .dataSource(
                        "jdbc:mysql://${instance.host}:$port/$DATABASE_NAME",
                        instance.username,
                        instance.password
                    )
                    .load()
            flyway.migrate()

            return connectionFactory
        }

        fun stop() {
            instance.stop()
        }
    }
}
