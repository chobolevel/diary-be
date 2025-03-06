package com.daangn.api.dto.categories

import com.daangn.domain.entity.categories.CategoryUpdateMask
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CreateCategoryRequestDto(
    @field:NotEmpty
    val iconUrl: String,
    @field:NotEmpty
    val name: String,
    @field:NotNull
    val order: Int
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UpdateCategoryRequestDto(
    val iconUrl: String?,
    val name: String?,
    val order: Int?,
    @field:Size(min = 1)
    val updateMask: List<CategoryUpdateMask>
)
