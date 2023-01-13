dependencies {
    implementation(project(":domain"))

    implementation("com.mysql:mysql-connector-j")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
}