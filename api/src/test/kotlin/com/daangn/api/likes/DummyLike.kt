package com.daangn.api.likes

import com.daangn.domain.entity.likes.Like
import com.daangn.domain.entity.likes.LikeType
import io.hypersistence.tsid.TSID

object DummyLike {
    private val id: String = TSID.fast().toString()
    private val type: LikeType = LikeType.POST
    private val targetId: String = TSID.fast().toString()
    private val createdAt: Long = 0L
    private val updatedAt: Long = 0L

    fun toEntity(): Like {
        return like
    }

    private val like: Like by lazy {
        Like(
            id = id,
            type = type,
            targetId = targetId,
        )
    }
}
