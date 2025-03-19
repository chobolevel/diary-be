package com.daangn.api.channels.messages

import com.daangn.domain.entity.channels.messages.ChannelMessage
import com.daangn.domain.entity.channels.messages.ChannelMessageType
import io.hypersistence.tsid.TSID

object DummyChannelMessage {
    private val id: String = TSID.fast().toString()
    private val type: ChannelMessageType = ChannelMessageType.TALK
    private val content: String = "테스트 메세지"
    private val createdAt: Long = 0L
    private val updatedAt: Long = 0L

    fun toEntity(): ChannelMessage {
        return channelMessage
    }

    private val channelMessage: ChannelMessage by lazy {
        ChannelMessage(
            id = id,
            type = type,
            content = content
        )
    }
}
