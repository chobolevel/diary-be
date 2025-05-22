package com.diary.api.dto.weathers

import com.diary.domain.entity.weathers.WeatherUpdateMask
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CreateWeatherRequestDto(
    @field:NotEmpty
    val name: String,
    @field:NotEmpty
    val icon: String,
    val order: Int?
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UpdateWeatherRequestDto(
    val name: String?,
    val icon: String?,
    val order: Int?,
    @field:Size(min = 1)
    val updateMask: List<WeatherUpdateMask>
)
