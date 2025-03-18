package com.daangn.api.channels

import com.daangn.api.dto.channels.ChannelResponseDto
import com.daangn.api.dto.channels.CreateChannelRequestDto
import com.daangn.api.dto.channels.UpdateChannelRequestDto
import com.daangn.domain.entity.channels.Channel
import com.daangn.domain.entity.channels.ChannelUpdateMask
import io.hypersistence.tsid.TSID

object DummyChannel {
    private val id: String = TSID.fast().toString()
    private val name: String = "채널명"
    private val createdAt: Long = 0L
    private val updatedAt: Long = 0L

    fun toCreateRequestDto(): CreateChannelRequestDto {
        return createRequest
    }
    fun toEntity(): Channel {
        return channel
    }
    fun toResponseDto(): ChannelResponseDto {
        return channelResponse
    }
    fun toUpdateRequestDto(): UpdateChannelRequestDto {
        return updateRequest
    }

    private val createRequest: CreateChannelRequestDto by lazy {
        CreateChannelRequestDto(
            name = name
        )
    }
    private val channel: Channel by lazy {
        Channel(
            id = id,
            name = name
        )
    }
    private val channelResponse: ChannelResponseDto by lazy {
        ChannelResponseDto(
            id = id,
            name = name,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }
    private val updateRequest: UpdateChannelRequestDto by lazy {
        UpdateChannelRequestDto(
            name = name,
            updateMask = listOf(
                ChannelUpdateMask.NAME
            )
        )
    }
}
