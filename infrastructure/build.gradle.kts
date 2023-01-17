dependencies {
    implementation(project(":domain"))

    /* database */
    implementation("com.mysql:mysql-connector-j")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")

    /* testing */
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("io.rest-assured:rest-assured")
    testImplementation("io.rest-assured:json-path")
    testImplementation("io.rest-assured:kotlin-extensions")
}
