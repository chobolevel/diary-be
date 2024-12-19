package com.scrimmers.api.service.scrim.query

import com.scrimmers.domain.dto.common.Pagination
import com.scrimmers.domain.entity.team.scrim.ScrimQueryFilter
import org.springframework.stereotype.Component

@Component
class ScrimQueryCreator {

    fun createQueryFilter(
        scrimRequestId: String?,
        teamId: String?,
    ): ScrimQueryFilter {
        return ScrimQueryFilter(
            scrimRequestId = scrimRequestId,
            teamId = teamId,
        )
    }

    fun createPaginationFilter(skipCount: Long?, limitCount: Long?): Pagination {
        val offset = skipCount ?: 0
        val limit = limitCount ?: 20
        return Pagination(
            offset = offset,
            limit = limit,
        )
    }
}
