package com.daangn.domain.entity.channels

import com.daangn.domain.entity.channels.QChannel.channel
import com.querydsl.core.types.dsl.BooleanExpression

data class ChannelQueryFilter(
    private val name: String?
) {

    fun toBooleanExpressions(): Array<BooleanExpression> {
        return listOfNotNull(
            name?.let { channel.name.eq(it) },
            channel.deleted.isFalse
        ).toTypedArray()
    }
}
