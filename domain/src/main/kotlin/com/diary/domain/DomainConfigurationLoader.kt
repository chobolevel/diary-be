package com.diary.domain

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@ComponentScan
@EnableJpaRepositories(basePackages = ["com.diary.domain.entity"])
@EntityScan(basePackages = ["com.diary.domain.entity"])
class DomainConfigurationLoader
