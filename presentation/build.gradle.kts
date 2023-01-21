dependencies {
    implementation(project(":application"))
    implementation(project(":domain"))

    /* spring */
    implementation("org.springframework.boot:spring-boot-starter-webflux")
}
