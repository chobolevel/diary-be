package com.diary.api.dto.products

import com.diary.api.dto.products.categories.ProductCategoryResponseDto
import com.diary.api.dto.products.options.ProductOptionResponseDto
import com.diary.domain.entity.products.ProductStatus
import com.diary.domain.type.ID
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ProductResponseDto(
    val id: ID,
    val category: ProductCategoryResponseDto,
    val name: String,
    val status: ProductStatus,
    val statusLabel: String,
    val order: Int,
    val productOptions: List<ProductOptionResponseDto>,
    val createdAt: Long,
    val updatedAt: Long,
)
