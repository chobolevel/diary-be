package com.daangn.api.dto.posts.likes

import com.daangn.api.dto.posts.PostResponseDto
import com.daangn.api.dto.users.UserResponseDto
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class PostLikeResponseDto(
    val id: String,
    val post: PostResponseDto,
    val user: UserResponseDto,
    val createdAt: Long,
    val updatedAt: Long
)
