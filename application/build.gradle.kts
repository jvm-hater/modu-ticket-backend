dependencies {
    implementation(project(":domain"))
    implementation(project(":infrastructure"))
    testImplementation(testFixtures(project(":domain")))

    /* spring */
    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-tx")
}
