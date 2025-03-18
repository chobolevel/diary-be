package com.daangn.domain.entity.channels

import com.daangn.domain.dto.Pagination
import com.daangn.domain.entity.channels.QChannel.channel
import com.daangn.domain.exception.DataNotFoundException
import com.daangn.domain.exception.ErrorCode
import com.querydsl.core.types.OrderSpecifier
import org.springframework.stereotype.Component
import kotlin.jvm.Throws

@Component
class ChannelRepositoryWrapper(
    private val repository: ChannelRepository,
    private val customRepository: ChannelCustomRepository
) {

    fun save(channel: Channel): Channel {
        return repository.save(channel)
    }

    fun search(
        queryFilter: ChannelQueryFilter,
        pagination: Pagination,
        orderTypes: List<ChannelOrderType>
    ): List<Channel> {
        return customRepository.searchByPredicates(
            booleanExpressions = queryFilter.toBooleanExpressions(),
            pagination = pagination,
            orderSpecifiers = orderTypes.toOrderSpecifiers()
        )
    }

    fun searchCount(
        queryFilter: ChannelQueryFilter,
    ): Long {
        return customRepository.countByPredicates(
            booleanExpressions = queryFilter.toBooleanExpressions(),
        )
    }

    @Throws(DataNotFoundException::class)
    fun findById(id: String): Channel {
        return repository.findByIdAndDeletedFalse(id) ?: throw DataNotFoundException(
            errorCode = ErrorCode.CHANNEL_NOT_FOUND,
            message = ErrorCode.CHANNEL_NOT_FOUND.message
        )
    }

    private fun List<ChannelOrderType>.toOrderSpecifiers(): Array<OrderSpecifier<*>> {
        return this.map {
            when (it) {
                ChannelOrderType.CREATED_AT_ASC -> channel.createdAt.asc()
                ChannelOrderType.CREATED_AT_DESC -> channel.createdAt.desc()
            }
        }.toTypedArray()
    }
}
