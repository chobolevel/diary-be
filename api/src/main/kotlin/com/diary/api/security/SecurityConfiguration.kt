package com.diary.api.security

import com.diary.api.properties.JwtProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfiguration(
    private val tokenProvider: TokenProvider,
    private val jwtProperties: JwtProperties,
) {

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .cors { config ->
                config.configurationSource(
                    UrlBasedCorsConfigurationSource().also { cors ->
                        CorsConfiguration().apply {
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
                                "Cookie"
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
            .csrf { csrf ->
                csrf.disable()
            }
            .sessionManagement { sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests { requests ->
                requests.anyRequest().permitAll()
            }
            .addFilterBefore(
                OnceJwtAuthorizationFilter(
                    tokenProvider = tokenProvider,
                    jwtProperties = jwtProperties
                ),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .build()
    }
}
