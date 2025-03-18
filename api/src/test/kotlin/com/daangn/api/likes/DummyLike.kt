package com.daangn.api.likes

import com.daangn.api.dto.likes.LikeRequestDto
import com.daangn.api.dto.likes.LikeResponseDto
import com.daangn.domain.entity.likes.Like
import com.daangn.domain.entity.likes.LikeType
import io.hypersistence.tsid.TSID

object DummyLike {
    private val id: String = TSID.fast().toString()
    private val type: LikeType = LikeType.POST
    private val targetId: String = TSID.fast().toString()
    private val createdAt: Long = 0L
    private val updatedAt: Long = 0L

    fun toRequestDto(): LikeRequestDto {
        return request
    }
    fun toEntity(): Like {
        return like
    }
    fun toResponseDto(): LikeResponseDto {
        return likeResponse
    }

    private val request: LikeRequestDto by lazy {
        LikeRequestDto(
            type = type,
            targetId = targetId,
        )
    }
    private val like: Like by lazy {
        Like(
            id = id,
            type = type,
            targetId = targetId,
        )
    }
    private val likeResponse: LikeResponseDto by lazy {
        LikeResponseDto(
            id = id,
            type = type,
            targetId = targetId,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }
}
