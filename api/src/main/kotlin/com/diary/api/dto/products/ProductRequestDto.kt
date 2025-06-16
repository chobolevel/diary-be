package com.diary.api.dto.products

import com.diary.domain.entity.products.ProductStatus
import com.diary.domain.entity.products.ProductUpdateMask
import com.diary.domain.type.ID
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import jakarta.validation.constraints.Size

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CreateProductRequestDto(
    val productCategoryId: ID,
    val name: String,
    val order: Int,
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UpdateProductRequestDto(
    val productCategoryId: ID?,
    val name: String?,
    val status: ProductStatus?,
    val order: Int?,
    @field:Size(min = 1)
    val updateMask: List<ProductUpdateMask>
)
