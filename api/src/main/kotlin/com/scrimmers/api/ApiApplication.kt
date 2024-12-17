package com.scrimmers.api

import com.scrimmers.domain.DomainConfigurationLoader
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@ConfigurationPropertiesScan(basePackages = ["com.scrimmers.api.properties"])
@Import(DomainConfigurationLoader::class)
@SpringBootApplication
class ApiApplication

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}
