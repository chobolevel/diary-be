package com.daangn.domain.entity.channels

import org.springframework.data.jpa.repository.JpaRepository

interface ChannelRepository : JpaRepository<Channel, String> {

    fun findByIdAndDeletedFalse(id: String): Channel?
}
