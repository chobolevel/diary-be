package com.daangn.domain.entity.channels.messages

import com.daangn.domain.dto.Pagination
import com.daangn.domain.entity.channels.messages.QChannelMessage.channelMessage
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class ChannelMessageCustomRepository : QuerydslRepositorySupport(ChannelMessage::class.java) {

    fun searchByPredicates(
        booleanExpressions: Array<BooleanExpression>,
        pagination: Pagination,
        orderSpecifiers: Array<OrderSpecifier<*>>
    ): List<ChannelMessage> {
        return from(channelMessage)
            .where(*booleanExpressions)
            .orderBy(*orderSpecifiers)
            .offset(pagination.offset)
            .limit(pagination.limit)
            .fetch()
    }

    fun countByPredicates(
        booleanExpressions: Array<BooleanExpression>,
    ): Long {
        return from(channelMessage)
            .where(*booleanExpressions)
            .fetchCount()
    }
}
