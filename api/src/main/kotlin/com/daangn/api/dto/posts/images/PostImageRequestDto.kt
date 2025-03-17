package com.daangn.api.dto.posts.images

import com.daangn.domain.entity.posts.image.PostImageType
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class PostImageRequestDto(
    @field:NotNull
    val type: PostImageType,
    @field:NotEmpty
    val url: String,
    @field:NotEmpty
    val name: String,
    @field:NotNull
    val order: Int,
)
