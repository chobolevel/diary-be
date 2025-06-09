package com.diary.api.dto.products.categories

import com.diary.domain.entity.products.categories.ProductCategoryUpdateMask
import com.diary.domain.type.ID
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CreateProductCategoryRequestDto(
    val parentId: ID?,
    @field:NotEmpty
    val name: String,
    val imageUrl: String?,
    @field:NotNull
    val order: Int
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UpdateProductCategoryRequestDto(
    val parentId: ID?,
    val name: String?,
    val imageUrl: String?,
    val order: Int?,
    @field:Size(min = 1)
    val updateMask: List<ProductCategoryUpdateMask>
)
