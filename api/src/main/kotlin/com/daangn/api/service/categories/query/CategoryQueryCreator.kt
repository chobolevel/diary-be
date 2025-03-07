package com.daangn.api.service.categories.query

import com.daangn.domain.dto.Pagination
import com.daangn.domain.entity.categories.CategoryQueryFilter
import org.springframework.stereotype.Component

@Component
class CategoryQueryCreator {

    fun createQueryFilter(
        name: String?
    ): CategoryQueryFilter {
        return CategoryQueryFilter(
            name = name
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
