package com.daangn.api.posts.image

import com.daangn.api.dto.posts.images.PostImageRequestDto
import com.daangn.api.dto.posts.images.PostImageResponseDto
import com.daangn.domain.entity.posts.image.PostImage
import com.daangn.domain.entity.posts.image.PostImageType
import io.hypersistence.tsid.TSID

object DummyPostImage {
    val id: String = TSID.fast().toString()
    val type: PostImageType = PostImageType.MAIN
    val url: String = "url"
    val name: String = "test.jpg"
    val order: Int = 1
    val createdAt: Long = 0L
    val updatedAt: Long = 0L

    fun toRequestDto(): PostImageRequestDto {
        return request
    }
    fun toEntity(): PostImage {
        return postImage
    }
    fun toResponseDto(): PostImageResponseDto {
        return response
    }

    private val request: PostImageRequestDto by lazy {
        PostImageRequestDto(
            type = type,
            url = url,
            name = name,
            order = order,
        )
    }
    private val postImage: PostImage by lazy {
        PostImage(
            id = id,
            type = type,
            url = url,
            name = name,
            order = order,
        )
    }
    private val response: PostImageResponseDto by lazy {
        PostImageResponseDto(
            id = id,
            type = type,
            url = url,
            name = name,
            order = order,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
}
