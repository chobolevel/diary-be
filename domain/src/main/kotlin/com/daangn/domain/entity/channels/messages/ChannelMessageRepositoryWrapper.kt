package com.daangn.domain.entity.channels.messages

import com.daangn.domain.dto.Pagination
import com.daangn.domain.entity.channels.messages.QChannelMessage.channelMessage
import com.daangn.domain.exception.DataNotFoundException
import com.daangn.domain.exception.ErrorCode
import com.querydsl.core.types.OrderSpecifier
import org.springframework.stereotype.Component
import kotlin.jvm.Throws

@Component
class ChannelMessageRepositoryWrapper(
    private val repository: ChannelMessageRepository,
    private val customRepository: ChannelMessageCustomRepository
) {

    fun save(channelMessage: ChannelMessage): ChannelMessage {
        return repository.save(channelMessage)
    }

    fun search(
        queryFilter: ChannelMessageQueryFilter,
        pagination: Pagination,
        orderTypes: List<ChannelMessageOrderType>
    ): List<ChannelMessage> {
        return customRepository.searchByPredicates(
            booleanExpressions = queryFilter.toBooleanExpressions(),
            pagination = pagination,
            orderSpecifiers = orderTypes.toOrderSpecifiers()
        )
    }

    fun searchCount(
        queryFilter: ChannelMessageQueryFilter
    ): Long {
        return customRepository.countByPredicates(
            booleanExpressions = queryFilter.toBooleanExpressions(),
        )
    }

    @Throws(DataNotFoundException::class)
    fun findById(id: String): ChannelMessage {
        return repository.findByIdAndDeletedFalse(id) ?: throw DataNotFoundException(
            errorCode = ErrorCode.CHANNEL_MESSAGE_NOT_FOUND,
            message = ErrorCode.CHANNEL_MESSAGE_NOT_FOUND.message
        )
    }

    private fun List<ChannelMessageOrderType>.toOrderSpecifiers(): Array<OrderSpecifier<*>> {
        return this.map {
            when (it) {
                ChannelMessageOrderType.CREATED_AT_ASC -> channelMessage.createdAt.asc()
                ChannelMessageOrderType.CREATED_AT_DESC -> channelMessage.createdAt.desc()
            }
        }.toTypedArray()
    }
}
