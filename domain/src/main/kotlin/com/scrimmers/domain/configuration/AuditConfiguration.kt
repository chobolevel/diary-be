package com.scrimmers.domain.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.auditing.DateTimeProvider
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.time.OffsetDateTime
import java.util.*

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "customDateTimeProvider")
class AuditConfiguration {

    @Bean
    fun customDateTimeProvider(): DateTimeProvider {
        return DateTimeProvider {
            Optional.of(OffsetDateTime.now())
        }
    }
}
