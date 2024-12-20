package com.scrimmers.api.service.scrim.updater

import com.scrimmers.api.dto.scrim.UpdateScrimRequestDto
import com.scrimmers.domain.entity.scrim.Scrim
import com.scrimmers.domain.entity.scrim.ScrimUpdateMask
import org.springframework.stereotype.Component

@Component
class ScrimUpdater {

    fun markAsUpdate(request: UpdateScrimRequestDto, entity: Scrim): Scrim {
        request.updateMask.forEach {
            when (it) {
                ScrimUpdateMask.TYPE -> entity.type = request.type!!
                ScrimUpdateMask.NAME -> entity.name = request.name!!
                ScrimUpdateMask.STARTED_AT -> entity.startedAt = request.startedAt!!
            }
        }
        return entity
    }
}
