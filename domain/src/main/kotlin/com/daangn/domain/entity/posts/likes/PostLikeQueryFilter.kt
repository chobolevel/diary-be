package com.daangn.domain.entity.posts.likes

import com.daangn.domain.entity.posts.likes.QPostLike.postLike
import com.querydsl.core.types.dsl.BooleanExpression

data class PostLikeQueryFilter(
    private val postId: String?,
    private val userId: String?,
) {

    fun toBooleanExpressions(): Array<BooleanExpression> {
        return listOfNotNull(
            postId?.let { postLike.post.id.eq(it) },
            userId?.let { postLike.user.id.eq(it) },
            postLike.deleted.isFalse,
        ).toTypedArray()
    }
}
