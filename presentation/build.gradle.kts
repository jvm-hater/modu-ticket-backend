dependencies {
    implementation(project(":application"))
    implementation(project(":domain"))
    testImplementation(testFixtures(project(":infrastructure")))

    /* spring */
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    /* swagger (https://springdoc.org/v2/) */
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.0.2")
}
