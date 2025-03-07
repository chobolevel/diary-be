package com.daangn.domain.entity.posts

import com.daangn.domain.entity.posts.QPost.post
import com.querydsl.core.types.dsl.BooleanExpression

data class PostQueryFilter(
    private val writerId: String?,
    private val categoryId: String?,
    private val title: String?
) {

    fun toBooleanExpressions(): Array<BooleanExpression> {
        return listOfNotNull(
            writerId?.let { post.writer.id.eq(it) },
            categoryId?.let { post.category.id.eq(it) },
            title?.let { post.title.startsWith(it) },
            post.deleted.isFalse
        ).toTypedArray()
    }
}
