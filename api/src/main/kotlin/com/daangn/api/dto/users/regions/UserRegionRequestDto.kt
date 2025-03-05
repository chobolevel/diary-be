package com.daangn.api.dto.users.regions

import com.daangn.domain.entity.users.regions.UserRegionUpdateMask
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CreateUserRegionRequestDto(
    @field:NotNull
    val latitude: Double,
    @field:NotNull
    val longitude: Double,
    @field:NotEmpty
    val firstDepthName: String,
    @field:NotEmpty
    val secondDepthName: String,
    @field:NotEmpty
    val thirdDepthName: String
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UpdateUserRegionRequestDto(
    val latitude: Double?,
    val longitude: Double?,
    val firstDepthName: String?,
    val secondDepthName: String?,
    val thirdDepthName: String?,
    @field:Size(min = 1)
    val updateMask: List<UserRegionUpdateMask>
)
