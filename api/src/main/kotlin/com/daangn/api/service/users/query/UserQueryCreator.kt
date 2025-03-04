package com.daangn.api.service.users.query

import com.daangn.domain.dto.Pagination
import com.daangn.domain.entity.users.UserQueryFilter
import com.daangn.domain.entity.users.UserRoleType
import com.daangn.domain.entity.users.UserSignUpType
import org.springframework.stereotype.Component

@Component
class UserQueryCreator {

    fun createQueryFilter(
        signUpType: UserSignUpType?,
        role: UserRoleType?,
        resigned: Boolean?
    ): UserQueryFilter {
        return UserQueryFilter(
            signUpType = signUpType,
            role = role,
            resigned = resigned
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
