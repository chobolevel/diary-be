package com.daangn.api.posts

import com.daangn.api.categories.DummyCategory
import com.daangn.api.dto.posts.CreatePostRequestDto
import com.daangn.api.dto.posts.PostResponseDto
import com.daangn.api.dto.posts.UpdatePostRequestDto
import com.daangn.api.posts.image.DummyPostImage
import com.daangn.api.users.DummyUser
import com.daangn.domain.entity.posts.Post
import com.daangn.domain.entity.posts.PostStatus
import com.daangn.domain.entity.posts.PostUpdateMask
import io.hypersistence.tsid.TSID

object DummyPost {
    private val id: String = TSID.fast().toString()
    private val status: PostStatus = PostStatus.ON_SALE
    private val title: String = "제목"
    private val content: String = "내용"
    private val salePrice: Int = 10_000
    private val freeShared: Boolean = false
    private val likeCount: Int = 0
    private val createdAt: Long = 0L
    private val updatedAt: Long = 0L

    fun toCreateRequestDto(): CreatePostRequestDto {
        return createRequest
    }
    fun toEntity(): Post {
        return post
    }
    fun toResponseDto(): PostResponseDto {
        return postResponse
    }
    fun toUpdateRequestDto(): UpdatePostRequestDto {
        return updateRequest
    }

    private val createRequest: CreatePostRequestDto by lazy {
        CreatePostRequestDto(
            categoryId = "0K0ZMTNDZX7Q4",
            title = title,
            content = content,
            salePrice = salePrice,
            freeShared = freeShared,
            mainImages = listOf(
                DummyPostImage.toRequestDto()
            )
        )
    }
    private val post: Post by lazy {
        Post(
            id = id,
            title = title,
            content = content,
            salePrice = salePrice,
            freeShared = freeShared,
        ).also {
            it.writer = DummyUser.toEntity()
            it.category = DummyCategory.toEntity()
        }
    }
    private val postResponse: PostResponseDto by lazy {
        PostResponseDto(
            id = id,
            writer = DummyUser.toResponseDto(),
            category = DummyCategory.toResponseDto(),
            status = status,
            title = title,
            content = content,
            salePrice = salePrice,
            isFreeShare = freeShared,
            likeCount = likeCount,
            mainImages = listOf(
                DummyPostImage.toResponseDto()
            ),
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }
    private val updateRequest: UpdatePostRequestDto by lazy {
        UpdatePostRequestDto(
            categoryId = null,
            status = null,
            title = null,
            content = null,
            salePrice = null,
            freeShared = true,
            mainImages = null,
            updateMask = listOf(
                PostUpdateMask.FREE_SHARED
            )
        )
    }
}
