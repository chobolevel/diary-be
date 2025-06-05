package com.diary.api.dto.users.points

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import jakarta.validation.constraints.NotEmpty
import org.jetbrains.annotations.NotNull

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class AddUserPointRequestDto(
    @field:NotNull
    val amount: Int,
    @field:NotEmpty
    val reason: String
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class SubUserPointRequestDto(
    @field:NotNull
    val amount: Int,
    @field:NotEmpty
    val reason: String
)
