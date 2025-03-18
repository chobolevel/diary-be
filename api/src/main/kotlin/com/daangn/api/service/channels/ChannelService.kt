package com.daangn.api.service.channels

import com.daangn.api.dto.channels.ChannelResponseDto
import com.daangn.api.dto.channels.CreateChannelRequestDto
import com.daangn.api.dto.channels.UpdateChannelRequestDto
import com.daangn.api.dto.common.PaginationResponseDto
import com.daangn.api.service.channels.converter.ChannelConverter
import com.daangn.api.service.channels.updater.ChannelUpdater
import com.daangn.domain.dto.Pagination
import com.daangn.domain.entity.channels.ChannelOrderType
import com.daangn.domain.entity.channels.ChannelQueryFilter
import com.daangn.domain.entity.channels.ChannelRepositoryWrapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChannelService(
    private val repositoryWrapper: ChannelRepositoryWrapper,
    private val converter: ChannelConverter,
    private val updater: ChannelUpdater
) {

    @Transactional
    fun create(userId: String, request: CreateChannelRequestDto): String {
        val channel = converter.convert(request)
        return repositoryWrapper.save(channel).id
    }

    @Transactional(readOnly = true)
    fun getChannels(
        queryFilter: ChannelQueryFilter,
        pagination: Pagination,
        orderTypes: List<ChannelOrderType>
    ): PaginationResponseDto {
        val channels = repositoryWrapper.search(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes
        )
        val totalCount = repositoryWrapper.searchCount(
            queryFilter = queryFilter,
        )
        return PaginationResponseDto(
            page = pagination.page,
            size = pagination.size,
            data = converter.convert(channels),
            totalCount = totalCount
        )
    }

    @Transactional(readOnly = true)
    fun getChannel(channelId: String): ChannelResponseDto {
        val channel = repositoryWrapper.findById(channelId)
        return converter.convert(channel)
    }

    @Transactional
    fun update(userId: String, channelId: String, request: UpdateChannelRequestDto): String {
        val channel = repositoryWrapper.findById(channelId)
        updater.markAsUpdate(
            request = request,
            entity = channel
        )
        return channelId
    }

    @Transactional
    fun delete(userId: String, channelId: String): Boolean {
        val channel = repositoryWrapper.findById(channelId)
        channel.delete()
        return true
    }
}
