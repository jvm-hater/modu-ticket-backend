plugins {
    id("org.springframework.boot") version "3.0.1"
    id("io.spring.dependency-management") version "1.1.0"
    id("com.diffplug.spotless") version "6.12.1"
    jacoco
    id("org.sonarqube") version "3.5.0.2730"
    id("java-test-fixtures")
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
}

/* 프로젝트 + 서브 모듈 build.gradle 제어 */
allprojects {
    group = "com.jvmhater"
    version = "0.0.1-SNAPSHOT"

    repositories { mavenCentral() }

    apply {
        plugin("kotlin")
        plugin("kotlin-spring")
        plugin("io.spring.dependency-management")
        plugin("com.diffplug.spotless")
        plugin("java-test-fixtures")
    }

    kotlin { jvmToolchain { languageVersion.set(JavaLanguageVersion.of(17)) } }

    dependencies {
        /* kotlin */
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
        implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
        //        implementation("org.springframework.boot:spring-boot-starter-security")
        //        implementation("io.jsonwebtoken:jjwt:0.9.1")

        /* testing */
        testImplementation("io.github.microutils:kotlin-logging-jvm:3.0.4")
        testImplementation("io.projectreactor:reactor-test")
        testImplementation("io.kotest:kotest-runner-junit5:5.5.4")
        testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.2")
        testImplementation("io.mockk:mockk:1.12.0")
        testImplementation("org.springframework.boot:spring-boot-starter-test")

        /* test fixtures */
        testFixturesImplementation("io.github.microutils:kotlin-logging-jvm:3.0.4")
        testFixturesImplementation("io.kotest:kotest-runner-junit5:5.5.4")
        testFixturesImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.2")
    }

    dependencyManagement {
        imports {
            mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
        }
    }

    spotless {
        kotlin { ktfmt("0.42").kotlinlangStyle() }
        kotlinGradle {
            target("*.gradle.kts")
            ktfmt("0.42").kotlinlangStyle()
        }
    }
}

/* 서브 모듈 build.gradle 제어 */
subprojects { tasks { test { useJUnitPlatform() } } }

/* build tasks */
tasks {
    bootJar { enabled = false }
    jar { enabled = true }

    compileKotlin {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    assemble {
        dependsOn(spotlessApply)
        shouldRunAfter(spotlessApply)
    }

    test {
        useJUnitPlatform()
        finalizedBy(jacocoTestReport)
    }
    jacoco { toolVersion = "0.8.8" }
    jacocoTestReport {
        reports {
            dependsOn(test)
            xml.required.set(true)
            csv.required.set(true)
            html.required.set(false)
        }
    }
}

sonarqube {
    properties {
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.organization", "jvm-hater")
        property("sonar.projectKey", "jvm-hater_modu-ticket-backend")
    }
}
