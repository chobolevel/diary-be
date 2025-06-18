package com.diary.api.dto.products.options

import com.diary.api.dto.products.options.values.CreateProductOptionValueRequestDto
import com.diary.domain.entity.products.options.ProductOptionUpdateMask
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CreateProductOptionRequestDto(
    @field:NotEmpty
    val name: String,
    @field:NotNull
    val order: Int,
    @field:Size(min = 1)
    val values: List<CreateProductOptionValueRequestDto>
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UpdateProductOptionRequestDto(
    val name: String?,
    val order: Int?,
    @field:Size(min = 1)
    val updateMask: List<ProductOptionUpdateMask>
)
