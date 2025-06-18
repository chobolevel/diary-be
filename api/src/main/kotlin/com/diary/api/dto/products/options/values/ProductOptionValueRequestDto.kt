package com.diary.api.dto.products.options.values

import com.diary.domain.entity.products.options.values.ProductOptionValueUpdateMask
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CreateProductOptionValueRequestDto(
    @field:NotEmpty
    val name: String,
    @field:NotNull
    val order: Int,
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UpdateProductOptionValueRequestDto(
    val name: String?,
    val order: Int?,
    @field:Size(min = 1)
    val updateMask: List<ProductOptionValueUpdateMask>
)
