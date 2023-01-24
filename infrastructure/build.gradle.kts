dependencies {
    implementation(project(":domain"))
    testImplementation(testFixtures(project(":domain")))

    /* database */
    implementation("mysql:mysql-connector-java")
    implementation("dev.miku:r2dbc-mysql:0.8.2.RELEASE")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-mysql:9.11.0")

    /* testing */
    testImplementation("org.testcontainers:testcontainers:1.17.6")
    testImplementation("org.testcontainers:mysql:1.17.6")
    testImplementation("org.testcontainers:r2dbc:1.17.6")
    testImplementation("org.testcontainers:junit-jupiter:1.17.6")
}
