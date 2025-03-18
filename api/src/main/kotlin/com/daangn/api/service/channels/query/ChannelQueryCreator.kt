package com.daangn.api.service.channels.query

import com.daangn.domain.dto.Pagination
import com.daangn.domain.entity.channels.ChannelQueryFilter
import org.springframework.stereotype.Component

@Component
class ChannelQueryCreator {

    fun createQueryFilter(name: String?): ChannelQueryFilter {
        return ChannelQueryFilter(
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
