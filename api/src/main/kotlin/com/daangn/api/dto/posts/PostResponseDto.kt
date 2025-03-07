package com.daangn.api.dto.posts

import com.daangn.api.dto.categories.CategoryResponseDto
import com.daangn.api.dto.users.UserResponseDto
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class PostResponseDto(
    val id: String,
    val writer: UserResponseDto,
    val category: CategoryResponseDto,
    val title: String,
    val content: String,
    val salePrice: Int,
    val isFreeShare: Boolean,
    val createdAt: Long,
    val updatedAt: Long
)
