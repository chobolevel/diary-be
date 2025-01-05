package com.scrimmers.api.service.board.query

import com.scrimmers.domain.dto.common.Pagination
import com.scrimmers.domain.entity.borad.BoardQueryFilter
import org.springframework.stereotype.Component

@Component
class BoardQueryCreator {

    fun createQueryFilter(writerId: String?, boardCategoryId: String?, title: String?): BoardQueryFilter {
        return BoardQueryFilter(
            writerId = writerId,
            boardCategoryId = boardCategoryId,
            title = title
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
