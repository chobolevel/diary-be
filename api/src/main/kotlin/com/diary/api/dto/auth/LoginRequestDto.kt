package com.diary.api.dto.auth

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import jakarta.validation.constraints.NotEmpty

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class LoginRequestDto(
    @field:NotEmpty
    val username: String,
    @field:NotEmpty
    val password: String,
)
