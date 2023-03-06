plugins {
    kotlin("jvm") version "1.8.0"

    id("org.springframework.boot") version "3.0.3"
    id("org.jetbrains.kotlin.plugin.spring") version "1.8.20-Beta"

    id("com.epages.restdocs-api-spec") version "0.17.1"


    application
}

group = "com.conpinion"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:3.0.3"))

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-webtestclient")

    testImplementation("com.epages:restdocs-api-spec:0.17.1") {
        exclude("org.springframework.boot")
    }
    testImplementation("com.epages:restdocs-api-spec-webtestclient:0.17.1")  {
        exclude("org.springframework.boot")
    }

    testImplementation("io.kotest:kotest-runner-junit5:5.5.5")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.2")
    testImplementation("io.kotest:kotest-assertions-core:5.5.5")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("MainKt")
}

openapi3 {
    setServer("https://profile.de/api")
    title = "Profile Service API"
    description = "API providing access to the profile service."
    version = "1.0.0"
    format = "yaml"
    outputDirectory = "build/unused"
    outputFileNamePrefix = "profile-service.openapi3"
}
