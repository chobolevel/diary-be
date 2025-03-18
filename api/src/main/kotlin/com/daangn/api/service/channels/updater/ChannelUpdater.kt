package com.daangn.api.service.channels.updater

import com.daangn.api.dto.channels.UpdateChannelRequestDto
import com.daangn.domain.entity.channels.Channel
import com.daangn.domain.entity.channels.ChannelUpdateMask
import org.springframework.stereotype.Component

@Component
class ChannelUpdater {

    fun markAsUpdate(
        request: UpdateChannelRequestDto,
        entity: Channel
    ): Channel {
        request.updateMask.forEach {
            when (it) {
                ChannelUpdateMask.NAME -> entity.name = request.name!!
            }
        }
        return entity
    }
}
