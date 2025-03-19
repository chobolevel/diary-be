package com.daangn.api.service.channels.converter

import com.daangn.api.dto.channels.ChannelResponseDto
import com.daangn.api.dto.channels.CreateChannelRequestDto
import com.daangn.api.service.users.converter.UserConverter
import com.daangn.domain.entity.channels.Channel
import io.hypersistence.tsid.TSID
import org.springframework.stereotype.Component

@Component
class ChannelConverter(
    private val userConverter: UserConverter
) {

    fun convert(request: CreateChannelRequestDto): Channel {
        return Channel(
            id = TSID.fast().toString(),
            name = request.name
        )
    }

    fun convert(entity: Channel): ChannelResponseDto {
        return ChannelResponseDto(
            id = entity.id,
            name = entity.name,
            channelUsers = entity.channelUsers.map { userConverter.convert(it.user!!) },
            createdAt = entity.createdAt!!.toInstant().toEpochMilli(),
            updatedAt = entity.updatedAt!!.toInstant().toEpochMilli()
        )
    }

    fun convert(entities: List<Channel>): List<ChannelResponseDto> {
        return entities.map { convert(it) }
    }
}
