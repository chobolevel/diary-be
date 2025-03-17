package com.daangn.api.dto.posts

import com.daangn.api.dto.categories.CategoryResponseDto
import com.daangn.api.dto.posts.images.PostImageResponseDto
import com.daangn.api.dto.users.UserResponseDto
import com.daangn.domain.entity.posts.PostStatus
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class PostResponseDto(
    val id: String,
    val writer: UserResponseDto,
    val category: CategoryResponseDto,
    val status: PostStatus,
    val title: String,
    val content: String,
    val salePrice: Int,
    val isFreeShare: Boolean,
    val likeCount: Int,
    val isLiked: Boolean,
    val mainImages: List<PostImageResponseDto>,
    val createdAt: Long,
    val updatedAt: Long
)
