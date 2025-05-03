package com.diary.api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.core.HashOperations
import org.springframework.data.redis.core.RedisTemplate

@Configuration
class RedisConfiguration(
    private val redisTemplate: RedisTemplate<String, String>,
) {

    @Bean
    fun hashOperations(): HashOperations<String, String, String> {
        return redisTemplate.opsForHash()
    }
}
