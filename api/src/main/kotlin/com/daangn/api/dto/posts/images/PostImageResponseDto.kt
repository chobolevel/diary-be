package com.daangn.api.dto.posts.images

import com.daangn.domain.entity.posts.image.PostImageType
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class PostImageResponseDto(
    val id: String,
    val type: PostImageType,
    val url: String,
    val order: Int,
    val createdAt: Long,
    val updatedAt: Long
)
