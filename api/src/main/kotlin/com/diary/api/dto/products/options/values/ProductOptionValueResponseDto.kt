package com.diary.api.dto.products.options.values

import com.diary.domain.type.ID
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ProductOptionValueResponseDto(
    val id: ID,
    val name: String,
    val order: Int,
    val createdAt: Long,
    val updatedAt: Long,
)
