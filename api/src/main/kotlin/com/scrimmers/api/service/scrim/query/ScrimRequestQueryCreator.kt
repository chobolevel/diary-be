package com.scrimmers.api.service.scrim.query

import com.scrimmers.domain.dto.common.Pagination
import com.scrimmers.domain.entity.team.scrim.request.ScrimRequestQueryFilter
import org.springframework.stereotype.Component

@Component
class ScrimRequestQueryCreator {

    fun createQueryFilter(fromTeamId: String?, toTeamId: String?): ScrimRequestQueryFilter {
        return ScrimRequestQueryFilter(
            fromTeamId = fromTeamId,
            toTeamId = toTeamId
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
