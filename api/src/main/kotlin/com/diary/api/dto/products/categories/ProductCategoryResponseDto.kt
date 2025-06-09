package com.diary.api.dto.products.categories

import com.diary.domain.type.ID
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ProductCategoryResponseDto(
    val id: ID,
    val name: String,
    val imageUrl: String?,
    val order: Int,
    val child: List<ProductCategoryResponseDto>,
    val createdAt: Long,
    val updatedAt: Long,
)
