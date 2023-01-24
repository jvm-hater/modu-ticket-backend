dependencies {
    implementation(project(":domain"))
    implementation(project(":infrastructure"))

    /* spring */
    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-tx")
}
