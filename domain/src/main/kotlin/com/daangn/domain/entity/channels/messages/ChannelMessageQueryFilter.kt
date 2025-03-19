package com.daangn.domain.entity.channels.messages

import com.daangn.domain.entity.channels.messages.QChannelMessage.channelMessage
import com.querydsl.core.types.dsl.BooleanExpression

data class ChannelMessageQueryFilter(
    private val channelId: String?,
) {

    fun toBooleanExpressions(): Array<BooleanExpression> {
        return listOfNotNull(
            channelId?.let { channelMessage.channel.id.eq(it) },
            channelMessage.deleted.isFalse
        ).toTypedArray()
    }
}
