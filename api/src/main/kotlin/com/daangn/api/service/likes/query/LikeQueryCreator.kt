package com.daangn.api.service.likes.query

import com.daangn.domain.dto.Pagination
import com.daangn.domain.entity.likes.LikeQueryFilter
import org.springframework.stereotype.Component

@Component
class LikeQueryCreator {

    fun createQueryFilter(userId: String?): LikeQueryFilter {
        return LikeQueryFilter(
            userId = userId
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
