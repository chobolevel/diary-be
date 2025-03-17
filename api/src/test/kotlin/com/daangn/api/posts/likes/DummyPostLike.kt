package com.daangn.api.posts.likes

import com.daangn.domain.entity.posts.likes.PostLike
import io.hypersistence.tsid.TSID

object DummyPostLike {
    private val id: String = TSID.fast().toString()
    private val createdAt: Long = 0L
    private val updatedAt: Long = 0L

    fun toEntity(): PostLike {
        return postLike
    }

    private val postLike: PostLike by lazy {
        PostLike(
            id = id,
        )
    }
}
