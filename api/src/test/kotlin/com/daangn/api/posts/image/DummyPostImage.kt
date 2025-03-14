package com.daangn.api.posts.image

import com.daangn.domain.entity.posts.image.PostImage
import com.daangn.domain.entity.posts.image.PostImageType
import io.hypersistence.tsid.TSID

object DummyPostImage {
    val id: String = TSID.fast().toString()
    val type: PostImageType = PostImageType.MAIN
    val url: String = "url"
    val order: Int = 1
    val createdAt: Long = 0L
    val updatedAt: Long = 0L

    fun toEntity(): PostImage {
        return postImage
    }

    private val postImage: PostImage by lazy {
        PostImage(
            id = id,
            type = type,
            url = url,
            order = order,
        )
    }
}
