package com.daangn.api.posts.likes

import com.daangn.api.dto.posts.likes.PostLikeResponseDto
import com.daangn.api.posts.DummyPost
import com.daangn.api.users.DummyUser
import com.daangn.domain.entity.posts.likes.PostLike
import io.hypersistence.tsid.TSID

object DummyPostLike {
    private val id: String = TSID.fast().toString()
    private val createdAt: Long = 0L
    private val updatedAt: Long = 0L

    fun toEntity(): PostLike {
        return postLike
    }
    fun toResponseDto(): PostLikeResponseDto {
        return postLikeResponse
    }

    private val postLike: PostLike by lazy {
        PostLike(
            id = id,
        )
    }
    private val postLikeResponse: PostLikeResponseDto by lazy {
        PostLikeResponseDto(
            id = id,
            post = DummyPost.toResponseDto(),
            user = DummyUser.toResponseDto(),
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }
}
