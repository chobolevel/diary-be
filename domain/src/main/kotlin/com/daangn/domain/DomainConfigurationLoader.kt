package com.daangn.domain

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@ComponentScan
@EnableJpaRepositories(basePackages = ["com.daangn.domain.entity"])
@EntityScan(basePackages = ["com.daangn.domain.entity"])
class DomainConfigurationLoader
