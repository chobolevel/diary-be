package com.scrimmers.api.service.team.query

import com.scrimmers.domain.dto.common.Pagination
import com.scrimmers.domain.entity.team.join.TeamJoinRequestQueryFilter
import org.springframework.stereotype.Component

@Component
class TeamJoinRequestQueryCreator {

    fun createQueryFilter(teamId: String?, userId: String?): TeamJoinRequestQueryFilter {
        return TeamJoinRequestQueryFilter(
            teamId = teamId,
            userId = userId
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
