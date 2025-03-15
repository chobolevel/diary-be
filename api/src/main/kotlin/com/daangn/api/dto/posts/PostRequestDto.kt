package com.daangn.api.dto.posts

import com.daangn.api.dto.posts.images.PostImageRequestDto
import com.daangn.domain.entity.posts.PostUpdateMask
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CreatePostRequestDto(
    @field:NotEmpty
    val categoryId: String,
    @field:NotEmpty
    val title: String,
    @field:NotEmpty
    val content: String,
    @field:NotNull
    val salePrice: Int,
    @field:NotNull
    val freeShared: Boolean,
    val mainImages: List<PostImageRequestDto>
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UpdatePostRequestDto(
    val categoryId: String?,
    val title: String?,
    val content: String?,
    val salePrice: Int?,
    val freeShared: Boolean?,
    val mainImages: List<PostImageRequestDto>?,
    @field:Size(min = 1)
    val updateMask: List<PostUpdateMask>
)
