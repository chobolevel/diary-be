package com.daangn.api.channels

import com.daangn.domain.entity.channels.Channel
import io.hypersistence.tsid.TSID

object DummyChannel {
    private val id: String = TSID.fast().toString()
    private val name: String = "채널명"
    private val createdAt: Long = 0L
    private val updatedAt: Long = 0L

    fun toEntity(): Channel {
        return channel
    }

    private val channel: Channel by lazy {
        Channel(
            id = id,
            name = name
        )
    }
}
