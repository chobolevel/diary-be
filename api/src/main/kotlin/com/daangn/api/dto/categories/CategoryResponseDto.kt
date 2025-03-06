package com.daangn.api.dto.categories

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CategoryResponseDto(
    val id: String,
    val iconUrl: String,
    val name: String,
    val order: Int,
    val createdAt: Long,
    val updatedAt: Long
)
