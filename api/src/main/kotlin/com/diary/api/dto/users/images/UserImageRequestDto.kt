package com.diary.api.dto.users.images

import com.diary.domain.entity.users.images.UserImageUpdateMask
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CreateUserImageRequestDto(
    @field:NotEmpty
    val name: String,
    @field:NotNull
    val width: Int,
    @field:NotNull
    val height: Int,
    @field:NotEmpty
    val url: String,
    @field:NotNull
    val order: Int,
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UpdateUserImageRequestDto(
    val name: String?,
    val width: Int?,
    val height: Int?,
    val url: String?,
    val order: Int?,
    @field:Size(min = 1)
    val updateMask: List<UserImageUpdateMask>
)
