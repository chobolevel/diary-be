package com.diary.api.dto.products.options

import com.diary.api.dto.products.options.values.ProductOptionValueResponseDto
import com.diary.domain.type.ID
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ProductOptionResponseDto(
    val id: ID,
    val name: String,
    val order: Int,
    val productOptionValues: List<ProductOptionValueResponseDto>,
    val createdAt: Long,
    val updatedAt: Long
)
