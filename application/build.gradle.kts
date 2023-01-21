dependencies {
    implementation(project(":domain"))
    implementation(project(":infrastructure"))
}

tasks.test {
    useJUnitPlatform()
}