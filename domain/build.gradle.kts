val queryDslVersion: String = "5.0.0"

plugins {
    kotlin("kapt")
    // no-args 생성자 생성
    kotlin("plugin.jpa")
    id("org.flywaydb.flyway")
}

// 해당 어노테이션 적용 시 all open 클래스로 생성
allOpen {
    annotations("jakarta.persistence.Entity")
    annotations("jakarta.persistence.MappedSuperclass")
    annotations("jakarta.persistence.Embeddable")
}

dependencies {
    // mysql
    runtimeOnly("com.mysql:mysql-connector-j")

    // web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // jpa
    api("org.springframework.boot:spring-boot-starter-data-jpa")

    // envers
    implementation("org.springframework.data:spring-data-envers")

    // querydsl
    implementation("com.querydsl:querydsl-jpa:$queryDslVersion:jakarta")

    // kapt(java annotation -> kotlin annotation)
    kapt("com.querydsl:querydsl-apt:$queryDslVersion:jakarta")
    kapt("jakarta.annotation:jakarta.annotation-api")
    kapt("jakarta.persistence:jakarta.persistence-api")
    kapt("org.springframework.boot:spring-boot-configuration-processor")
}

flyway {
    url = "jdbc:mysql://localhost:3306/scrimmers"
    user = "root"
    password = "1234"
    baselineVersion = "0"
    outOfOrder = true
}
