package com.daangn.domain.entity.channels

import com.daangn.domain.dto.Pagination
import com.daangn.domain.entity.channels.QChannel.channel
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class ChannelCustomRepository : QuerydslRepositorySupport(Channel::class.java) {

    fun searchByPredicates(
        booleanExpressions: Array<BooleanExpression>,
        pagination: Pagination,
        orderSpecifiers: Array<OrderSpecifier<*>>
    ): List<Channel> {
        return from(channel)
            .where(*booleanExpressions)
            .orderBy(*orderSpecifiers)
            .offset(pagination.offset)
            .limit(pagination.limit)
            .fetch()
    }

    fun countByPredicates(
        booleanExpressions: Array<BooleanExpression>
    ): Long {
        return from(channel)
            .where(*booleanExpressions)
            .fetchCount()
    }
}
