import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.8.21"
    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion apply false
    kotlin("plugin.spring") version kotlinVersion apply false
    kotlin("plugin.jpa") version kotlinVersion apply false
    id("org.springframework.boot") version "3.1.0" apply false
    id("io.spring.dependency-management") version "1.1.0" apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.3.1" apply false
    id("org.flywaydb.flyway") version "7.13.0" apply false
    id("com.google.cloud.tools.jib") version "3.1.2" apply false
}

allprojects {
    group = "com.scrimmers"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    apply {
        plugin("org.jlleitschuh.gradle.ktlint")
    }
}

subprojects {
    val kotestVersion = "5.5.5"
    val jacksonVersion = "2.14.2"

    apply {
        plugin("kotlin")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("org.jetbrains.kotlin.plugin.spring")
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect")

        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
        implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")

        // test
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
        testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
        testImplementation("io.kotest:kotest-property:$kotestVersion")
        testImplementation("io.kotest:kotest-framework-datatest:$kotestVersion")
        testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.2")
        testImplementation("io.mockk:mockk:1.13.4")
    }

    ext {
        set("springBootVersion", "3.4.0")

        set("baseJvmFlags", { memory: String, imageTag: String?, stage: String? ->
            listOf(
                "-server",
                "-Xms$memory",
                "-Xmx$memory",
                "-XX:+UseContainerSupport",
                "-Dspring.profiles.active=$stage",
                "-XX:+UseStringDeduplication",
                "-Dfile.encoding=UTF8",
                "-Dsun.net.inetaddr.ttl=0",
                "-Dtag=$imageTag",
                "-Djasypt.encryptor.password=chobolevel"
            )
        })
        set("dockerEnv", { stage: String?, port: String, applicationUser: String ->
            mapOf(
                "SPRING_PROFILES_ACTIVE" to stage,
                "TZ" to "Asia/Seoul",
                "PORT" to port,
                "APPLICATION_USER" to applicationUser
            )
        })
        set("dockerUser", "nobody")
        set("containerCreationTime", "USE_CURRENT_TIMESTAMP")
        set("dockerBaseImage", "eclipse-temurin:17.0.6_10-jdk-alpine")
    }

    tasks {
        withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = "17"
            }
        }

        withType<JavaCompile> {
            sourceCompatibility = "17"
            targetCompatibility = "17"
        }

        withType<Test> {
            useJUnitPlatform()
        }
    }
}
