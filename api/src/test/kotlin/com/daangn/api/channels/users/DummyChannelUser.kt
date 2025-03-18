package com.daangn.api.channels.users

import com.daangn.domain.entity.channels.users.ChannelUser
import com.daangn.domain.entity.channels.users.ChannelUserGrade
import io.hypersistence.tsid.TSID

object DummyChannelUser {
    private val id: String = TSID.fast().toString()
    private val grade: ChannelUserGrade = ChannelUserGrade.GENERAL
    private val createdAt: Long = 0L
    private val updatedAt: Long = 0L

    fun toEntity(): ChannelUser {
        return channelUser
    }

    private val channelUser: ChannelUser by lazy {
        ChannelUser(
            id = id,
            grade = grade
        )
    }
}
