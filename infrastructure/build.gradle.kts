dependencies {
    implementation(project(":domain"))
    testImplementation(project(":domain"))

    /* kotlin */
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    /* database */
    implementation("mysql:mysql-connector-java")
    implementation("dev.miku:r2dbc-mysql:0.8.2.RELEASE")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-mysql:9.11.0")

    /* testing */
    testImplementation("io.github.microutils:kotlin-logging-jvm:3.0.4")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("io.kotest:kotest-runner-junit5:5.5.4")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.2")
    testImplementation("org.testcontainers:testcontainers:1.17.6")
    testImplementation("org.testcontainers:mysql:1.17.6")
    testImplementation("org.testcontainers:r2dbc:1.17.6")
    testImplementation("org.testcontainers:junit-jupiter:1.17.6")
}
