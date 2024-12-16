val queryDslVersion: String = "5.0.0"

plugins {
    id("org.flywaydb.flyway")
}

dependencies {
    // mysql
    runtimeOnly("com.mysql:mysql-connector-j")

    // jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // envers
    implementation("org.springframework.data:spring-data-envers")

    // querydsl
    implementation("com.querydsl:querydsl-jpa:${queryDslVersion}:jakarta")
}

flyway {
    url = "jdbc:mysql://localhost:3306/scrimmers"
    user = "root"
    password = "1234"
    baselineVersion = "0"
    outOfOrder = true
}
