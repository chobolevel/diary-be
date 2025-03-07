package com.daangn.api.service.posts.query

import com.daangn.domain.dto.Pagination
import com.daangn.domain.entity.posts.PostQueryFilter
import org.springframework.stereotype.Component

@Component
class PostQueryCreator {

    fun createQueryFilter(
        writerId: String?,
        categoryId: String?,
        title: String?,
    ): PostQueryFilter {
        return PostQueryFilter(
            writerId = writerId,
            categoryId = categoryId,
            title = title,
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
