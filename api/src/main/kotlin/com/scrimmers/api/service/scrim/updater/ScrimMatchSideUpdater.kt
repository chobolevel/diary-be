package com.scrimmers.api.service.scrim.updater

import com.scrimmers.api.dto.scrim.match.side.UpdateScrimMatchSideRequestDto
import com.scrimmers.domain.entity.scrim.match.side.ScrimMatchSide
import com.scrimmers.domain.entity.scrim.match.side.ScrimMatchSideUpdateMask
import org.springframework.stereotype.Component

@Component
class ScrimMatchSideUpdater {

    fun markAsUpdate(request: UpdateScrimMatchSideRequestDto, entity: ScrimMatchSide): ScrimMatchSide {
        request.updateMask.forEach {
            when (it) {
                ScrimMatchSideUpdateMask.KILL_SCORE -> entity.killScore = request.killScore!!
                ScrimMatchSideUpdateMask.TOTAL_GOLD -> entity.totalGold = request.totalGold!!
            }
        }
        return entity
    }
}
