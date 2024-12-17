package com.scrimmers.domain

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@ComponentScan
@EnableJpaRepositories(basePackages = ["com.scrimmers.domain.entity"])
@EntityScan(basePackages = ["com.scrimmers.domain.entity"])
class DomainConfigurationLoader
