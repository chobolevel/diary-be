package com.daangn.api.posts

import com.daangn.domain.entity.posts.Post
import io.hypersistence.tsid.TSID

object DummyPost {
    private val id: String = TSID.fast().toString()
    private val title: String = "제목"
    private val content: String = "내용"
    private val salePrice: Int = 10_000
    private val freeShared: Boolean = false
    private val createdAt: Long = 0L
    private val updatedAt: Long = 0L

    fun toEntity(): Post {
        return post
    }

    private val post: Post by lazy {
        Post(
            id = id,
            title = title,
            content = content,
            salePrice = salePrice,
            freeShared = freeShared,
        )
    }
}
