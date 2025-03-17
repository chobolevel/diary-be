package com.daangn.api.service.posts.query

import com.daangn.domain.dto.Pagination
import com.daangn.domain.entity.posts.likes.PostLikeQueryFilter
import org.springframework.stereotype.Component

@Component
class PostLikeQueryCreator {

    fun createQueryFilter(
        postId: String?,
        userId: String?,
    ): PostLikeQueryFilter {
        return PostLikeQueryFilter(
            postId = postId,
            userId = userId,
        )
    }

    fun createPaginationFilter(
        page: Long?,
        size: Long?
    ): Pagination {
        return Pagination(
            page = page ?: 1,
            size = size ?: 20
        )
    }
}
