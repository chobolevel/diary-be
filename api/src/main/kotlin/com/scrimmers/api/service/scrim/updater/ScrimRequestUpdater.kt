package com.scrimmers.api.service.scrim.updater

import com.scrimmers.api.dto.scrim.request.UpdateScrimRequestRequestDto
import com.scrimmers.domain.entity.team.scrim.request.ScrimRequest
import com.scrimmers.domain.entity.team.scrim.request.ScrimRequestUpdateMask
import org.springframework.stereotype.Component

@Component
class ScrimRequestUpdater {

    fun markAsUpdate(request: UpdateScrimRequestRequestDto, entity: ScrimRequest): ScrimRequest {
        request.updateMask.forEach {
            when (it) {
                ScrimRequestUpdateMask.COMMENT -> entity.comment = request.comment!!
            }
        }
        return entity
    }
}
