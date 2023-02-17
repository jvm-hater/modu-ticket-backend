dependencies {
    implementation(project(":domain"))
    testImplementation(testFixtures(project(":domain")))

    /* database */
    implementation("mysql:mysql-connector-java")
    implementation("com.github.jasync-sql:jasync-r2dbc-mysql:2.1.16")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-mysql:9.11.0")

    /* test fixtures */
    testFixturesImplementation("mysql:mysql-connector-java")
    testFixturesImplementation("com.github.jasync-sql:jasync-r2dbc-mysql:2.1.16")
    testFixturesImplementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    testFixturesImplementation("org.flywaydb:flyway-core")
    testFixturesImplementation("org.flywaydb:flyway-mysql:9.11.0")
    testFixturesImplementation("org.testcontainers:testcontainers:1.17.6")
    testFixturesImplementation("org.testcontainers:mysql:1.17.6")
    testFixturesImplementation("org.testcontainers:r2dbc:1.17.6")
    testFixturesImplementation("org.testcontainers:junit-jupiter:1.17.6")
    testFixturesImplementation("org.testcontainers:elasticsearch:1.17.6")
    testFixturesImplementation(project(":domain"))
}
