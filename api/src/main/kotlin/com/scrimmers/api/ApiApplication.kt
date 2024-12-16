package com.scrimmers.api

import com.scrimmers.domain.DomainConfigurationLoader
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EntityScan(basePackages = ["com.scrimmers.domain"])
@EnableJpaRepositories(basePackages = ["com.scrimmers.domain"])
@Import(DomainConfigurationLoader::class)
@SpringBootApplication
class ApiApplication

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}
