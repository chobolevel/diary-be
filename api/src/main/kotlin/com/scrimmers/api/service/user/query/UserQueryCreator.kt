package com.scrimmers.api.service.user.query

import com.scrimmers.domain.dto.common.Pagination
import com.scrimmers.domain.entity.user.UserLoginType
import com.scrimmers.domain.entity.user.UserQueryFilter
import com.scrimmers.domain.entity.user.UserRoleType
import org.springframework.stereotype.Component

@Component
class UserQueryCreator {

    fun createQueryFilter(
        teamId: String?,
        loginType: UserLoginType?,
        role: UserRoleType?,
        resigned: Boolean?
    ): UserQueryFilter {
        return UserQueryFilter(
            teamId = teamId,
            loginType = loginType,
            role = role,
            resigned = resigned
        )
    }

    fun createPaginationFilter(skipCount: Long?, limitCount: Long?): Pagination {
        val skip = skipCount ?: 0
        val limit = limitCount ?: 20
        return Pagination(
            offset = skip,
            limit = limit
        )
    }
}
