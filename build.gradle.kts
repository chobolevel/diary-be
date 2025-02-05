import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// 필요한 플러그인을 관리하는 블럭(gradle 5.0 이후로 buildscript 보다 많이 사용됨)
plugins {
    val kotlinVersion = "1.8.21"
    val ktlintVersion = "11.3.1"
    val springBootVersion = "3.1.0"
    val springDependencyManagementVersion = "1.1.0"
    val flywayVersion = "7.13.0"
    val jibVersion = "3.4.4"

    // kotlin("plugin") 형식을 사용하면 kotlin 플러그인의 하위 플러그인을 사용하는 방식
    // 즉 코틀린 관련 플러그인을 사용할 때의 방식
    // 반면에 id("plugin") 형식을 사욜하면 식별자를 통해 해당 플러그인을 적용

    // kotlin jvm 플러그인을 사용하여 kotlin 코드를 컴파일
    kotlin("jvm") version kotlinVersion
    // kotlin annotation processing 플러그인을 사용하여 컴파일 시점에 어노테이션을 처리
    kotlin("kapt") version kotlinVersion apply false
    kotlin("plugin.spring") version kotlinVersion apply false
    kotlin("plugin.jpa") version kotlinVersion apply false
    // kotlin 파일 lint 도구
    id("org.jlleitschuh.gradle.ktlint") version ktlintVersion
    // spring boot 애플리케이션 빌드 및 실행에 필요한 여러 기능 제공
    id("org.springframework.boot") version springBootVersion
    // spring boot 의존성 관리를 쉽게 하기 위한 스크립트를 제공
    id("io.spring.dependency-management") version springDependencyManagementVersion
    // local db migration 도구
    id("org.flywaydb.flyway") version flywayVersion apply false
    // jib 도구
    id("com.google.cloud.tools.jib") version jibVersion apply false

    // apply false 적용한 경우 루트 선언한 루트 프로젝트에서는 사용하지 않지만 하위 프로젝트에서는 버전 없이 사용 가능
}

// 루트 + 서브 프로젝트 관리
allprojects {
    group = "com.daangn"
    version = "0.0.1-SNAPSHOT"

    apply {
        plugin("kotlin")
    }

    repositories {
        mavenCentral()
    }
}

// 서브 프로젝트 관리
subprojects {
    apply {
        // plugins 블록에서 설정한 플러그인 적용
        plugin("org.jlleitschuh.gradle.ktlint")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("org.jetbrains.kotlin.plugin.spring")
    }

    dependencies {
        // implementation = 해당 프로젝트에만 사용되고 다른 프로젝트에 전파되지 않음
        // api = 해당 프로젝트를 의존하는 다른 프로젝트에서도 사용될 수 있어야 하는 경우 사용
        // compileOnly = 컴파일 시에만 필요한 경우 선언
        // runtimeOnly = 런타임 시에만 필요한 경우 선언
        // testImplementation = 테스트 코드에만 사용하는 의존성 선언
        // testCompileOnly = 테스트 코드에서 컴파일 시에만 사용되는 의존성 선언
        // testRuntimeOnly = 테스트 코드에서 런타임 시에만 사용되는 의존성 선언
        // annotationProcessor = 어노테이션 프로세서에서 사용하는 의존성

        // spring-boot-starter
        implementation("org.springframework.boot:spring-boot-starter-web")

        // jackson 라이브러리와 kotlin 호환성을 위해 사용(data 클래스, nullish, @JsonProperty 등)
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

        // 애플리케이션 실행 중에 객체의 클래스 정보를 동적으로 조회하거나 조작할 수 있는 기능(User::class)
        implementation("org.jetbrains.kotlin:kotlin-reflect")

        // junit
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }

    tasks {
        withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = "17"
            }
        }

        withType<Test> {
            useJUnitPlatform()
        }
    }
}
