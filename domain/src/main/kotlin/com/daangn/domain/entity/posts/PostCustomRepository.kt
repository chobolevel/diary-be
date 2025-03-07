package com.daangn.domain.entity.posts

import com.daangn.domain.dto.Pagination
import com.daangn.domain.entity.posts.QPost.post
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class PostCustomRepository : QuerydslRepositorySupport(Post::class.java) {

    fun searchByPredicates(
        booleanExpressions: Array<BooleanExpression>,
        pagination: Pagination,
        orderSpecifiers: Array<OrderSpecifier<*>>
    ): List<Post> {
        return from(post)
            .where(*booleanExpressions)
            .orderBy(*orderSpecifiers)
            .offset(pagination.offset)
            .limit(pagination.limit)
            .fetch()
    }

    fun countByPredicates(
        booleanExpressions: Array<BooleanExpression>,
    ): Long {
        return from(post)
            .where(*booleanExpressions)
            .fetchCount()
    }
}
