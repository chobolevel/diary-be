package com.daangn.domain.entity.posts.likes

import org.springframework.data.jpa.repository.JpaRepository

interface PostLikeRepository : JpaRepository<PostLike, String> {

    fun findByIdAndDeletedFalse(id: String): PostLike?

    fun findByPostIdAndUserIdAndDeletedFalse(postId: String, userId: String): PostLike?
}
