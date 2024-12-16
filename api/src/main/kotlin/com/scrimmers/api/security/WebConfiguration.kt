package com.scrimmers.api.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class WebConfiguration {

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http.cors { config ->
            config.configurationSource(
                UrlBasedCorsConfigurationSource().also { cors ->
                    CorsConfiguration().apply {
                        allowedOrigins = listOf("*")
                        allowedMethods = listOf("POST", "PUT", "DELETE", "GET", "OPTIONS", "HEAD")
                        allowedHeaders = listOf(
                            "Authorization",
                            "Content-Type",
                            "X-Requested-With",
                            "Accept",
                            "Origin",
                            "Access-Control-Request-Method",
                            "Access-Control-Request-Headers",
                            "Access-Control-Allow-Origin",
                            "Access-Control-Allow-Credentials",
                        )
                        exposedHeaders = listOf(
                            "Access-Control-Allow-Origin",
                            "Access-Control-Allow-Credentials",
                            "Authorization",
                            "Content-Disposition",
                            "Set-Cookie"
                        )
                        maxAge = 3600
                        allowCredentials = true
                        cors.registerCorsConfiguration("/**", this)
                    }
                }
            )
        }
            .csrf { csrf -> csrf.disable() }
            .sessionManagement { sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests { authorizeRequests ->
                authorizeRequests
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .requestMatchers("/api/**").permitAll()
                    .anyRequest().permitAll()
            }
            .build()
    }
}
