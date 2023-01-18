dependencies {
    implementation(project(":application"))
    implementation(project(":domain"))

    /* kotlin */
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    /* spring */
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    /* testing */
    testImplementation("io.github.microutils:kotlin-logging-jvm:3.0.4")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("io.kotest:kotest-runner-junit5:5.5.4")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.2")
}
