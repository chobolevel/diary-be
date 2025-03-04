package com.daangn.api.dto.users.regions

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UserRegionResponseDto(
    val id: String,
    val latitude: Double,
    val longitude: Double,
    val region1depthName: String,
    val region2depthName: String,
    val region3depthName: String,
    val createdAt: Long,
    val updatedAt: Long
)
