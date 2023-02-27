dependencies {
    implementation(project(":application"))
    implementation(project(":domain"))
    implementation(project(":infrastructure"))
    testImplementation(testFixtures(project(":domain")))
    testImplementation(testFixtures(project(":infrastructure")))

    /* swagger (https://springdoc.org/v2/) */
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.0.2")

    /* test fixtures */
    testFixturesImplementation(project(":domain"))
}
