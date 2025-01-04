package com.scrimmers.api.service.board.query

import com.scrimmers.domain.dto.common.Pagination
import com.scrimmers.domain.entity.borad.category.BoardCategoryQueryFilter
import org.springframework.stereotype.Component

@Component
class BoardCategoryQueryCreator {

    fun createQueryFilter(code: String?, name: String?): BoardCategoryQueryFilter {
        return BoardCategoryQueryFilter(
            code = code,
            name = name
        )
    }

    fun createPaginationFilter(skipCount: Long?, limitCount: Long?): Pagination {
        val offset = skipCount ?: 0
        val limit = limitCount ?: 20
        return Pagination(
            offset = offset,
            limit = limit
        )
    }
}
