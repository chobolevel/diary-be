import org.springframework.boot.gradle.tasks.bundling.BootJar

val queryDslVersion: String = "5.0.0"

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true

plugins {
    kotlin("kapt")
    kotlin("plugin.jpa")
    id("org.flywaydb.flyway")
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
}

flyway {
    url = "jdbc:mysql://localhost:3306/diary"
    user = "root"
    password = "1234"
    baselineVersion = "0"
    outOfOrder = true
}

dependencies {
    // jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // querydsl
    implementation("com.querydsl:querydsl-jpa:$queryDslVersion:jakarta")
    kapt("com.querydsl:querydsl-apt:$queryDslVersion:jakarta")

    // envers
    implementation("org.springframework.data:spring-data-envers")

    // mysql
    runtimeOnly("com.mysql:mysql-connector-j")
}
