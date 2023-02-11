package com.jvmhater.moduticket.testcontainers

import com.github.jasync.r2dbc.mysql.MysqlConnectionFactoryProvider
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

        private fun getConnectionFactoryOption(): ConnectionFactoryOptions {
            return MySQLR2DBCDatabaseContainer.getOptions(instance)
        }

        /*
        TODO
        테스트 컨테이너가 r2dbc 에 의존성을 갖고 있음.
        databaseClient, connectionFactory, sql() 을 따로 분리하는 것이 좋아보임.
        r2dbc 관련 코드를 분리하면 gradle 파일에서 testFixturesImpl 도 제거해야 함.
        */
        fun sql(sql: String) {
            databaseClient.sql(sql).then().block()
        }

        fun start(): DatabaseProperty {
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
                MysqlConnectionFactoryProvider().create(getConnectionFactoryOption())
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

            return DatabaseProperty(
                host = instance.host,
                port = instance.getMappedPort(MYSQL_PORT),
                databaseName = instance.databaseName,
                username = instance.username,
                password = instance.password
            )
        }

        fun stop() {
            instance.stop()
        }
    }

    data class DatabaseProperty(
        val host: String,
        val port: Int,
        val databaseName: String,
        val username: String,
        val password: String
    )
}
