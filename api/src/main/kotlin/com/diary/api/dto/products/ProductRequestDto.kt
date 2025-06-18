package com.diary.api.dto.products

import com.diary.api.dto.products.options.CreateProductOptionRequestDto
import com.diary.domain.entity.products.ProductStatus
import com.diary.domain.entity.products.ProductUpdateMask
import com.diary.domain.type.ID
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CreateProductRequestDto(
    @field:NotNull
    val productCategoryId: ID,
    @field:NotEmpty
    val name: String,
    @field:NotNull
    val order: Int,
    @field:Size(min = 1)
    val options: List<CreateProductOptionRequestDto>
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
