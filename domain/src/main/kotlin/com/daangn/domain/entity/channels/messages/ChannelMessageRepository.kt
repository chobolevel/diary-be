package com.daangn.domain.entity.channels.messages

import org.springframework.data.jpa.repository.JpaRepository

interface ChannelMessageRepository : JpaRepository<ChannelMessage, String> {

    fun findByIdAndDeletedFalse(id: String): ChannelMessage?
}
