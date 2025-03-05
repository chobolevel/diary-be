package com.daangn.api.service.users.query

import com.daangn.domain.dto.Pagination
import com.daangn.domain.entity.users.regions.UserRegionQueryFilter
import org.springframework.stereotype.Component

@Component
class UserRegionQueryCreator {

    fun createQueryFilter(
        userId: String?
    ): UserRegionQueryFilter {
        return UserRegionQueryFilter(
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
