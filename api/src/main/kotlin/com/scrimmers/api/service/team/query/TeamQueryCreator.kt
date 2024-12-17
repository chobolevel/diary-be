package com.scrimmers.api.service.team.query

import com.scrimmers.domain.dto.common.Pagination
import com.scrimmers.domain.entity.team.TeamQueryFilter
import org.springframework.stereotype.Component

@Component
class TeamQueryCreator {

    fun createQueryFilter(ownerId: String?, name: String?): TeamQueryFilter {
        return TeamQueryFilter(
            ownerId = ownerId,
            name = name,
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
