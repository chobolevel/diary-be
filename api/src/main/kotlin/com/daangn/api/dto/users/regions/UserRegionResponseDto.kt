package com.daangn.api.dto.users.regions

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UserRegionResponseDto(
    val id: String,
    val latitude: Double,
    val longitude: Double,
    val firstDepthName: String,
    val secondDepthName: String,
    val thirdDepthName: String,
    val createdAt: Long,
    val updatedAt: Long
)
