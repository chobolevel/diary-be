package com.scrimmers.api.service.scrim.updater

import com.scrimmers.api.dto.scrim.match.UpdateScrimMatchRequestDto
import com.scrimmers.domain.entity.scrim.match.ScrimMatch
import com.scrimmers.domain.entity.scrim.match.ScrimMatchUpdateMask
import com.scrimmers.domain.entity.scrim.match.side.ScrimMatchSideType
import org.springframework.stereotype.Component

@Component
class ScrimMatchUpdater(
    private val scrimMatchSideUpdater: ScrimMatchSideUpdater
) {

    fun markAsUpdate(request: UpdateScrimMatchRequestDto, entity: ScrimMatch): ScrimMatch {
        request.updateMask.forEach {
            when (it) {
                ScrimMatchUpdateMask.WINNER_SIDE -> entity.winnerSide = request.winnerSide!!
                ScrimMatchUpdateMask.BLUE_TEAM -> {
                    scrimMatchSideUpdater.markAsUpdate(
                        request.blueTeam!!,
                        entity.scrimMatchSides.find { it.side == ScrimMatchSideType.BLUE }!!
                    )
                }

                ScrimMatchUpdateMask.RED_TEAM -> {
                    scrimMatchSideUpdater.markAsUpdate(
                        request.redTeam!!,
                        entity.scrimMatchSides.find { it.side == ScrimMatchSideType.RED }!!
                    )
                }
            }
        }
        return entity
    }
}
