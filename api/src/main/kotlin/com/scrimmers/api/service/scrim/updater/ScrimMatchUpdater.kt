package com.scrimmers.api.service.scrim.updater

import com.scrimmers.api.dto.scrim.match.UpdateScrimMatchRequestDto
import com.scrimmers.domain.entity.scrim.match.ScrimMatch
import com.scrimmers.domain.entity.scrim.match.ScrimMatchUpdateMask
import com.scrimmers.domain.entity.scrim.match.side.ScrimMatchSideType
import com.scrimmers.domain.entity.user.UserFinder
import com.scrimmers.domain.exception.ErrorCode
import com.scrimmers.domain.exception.PolicyException
import org.springframework.stereotype.Component

@Component
class ScrimMatchUpdater(
    private val scrimMatchSideUpdater: ScrimMatchSideUpdater,
    private val userFinder: UserFinder
) {

    fun markAsUpdate(request: UpdateScrimMatchRequestDto, entity: ScrimMatch): ScrimMatch {
        request.updateMask.forEach {
            when (it) {
                ScrimMatchUpdateMask.WINNER_SIDE -> entity.winnerSide = request.winnerSide!!
                ScrimMatchUpdateMask.ORDER -> entity.order = request.order!!
                ScrimMatchUpdateMask.POG_USER -> {
                    val homeTeamUserIds = userFinder.findByTeamId(entity.scrim!!.homeTeam!!.id).map { it.id }
                    val awayTeamUserIds = userFinder.findByTeamId(entity.scrim!!.awayTeam!!.id).map { it.id }
                    val scrimUserIds = homeTeamUserIds.union(awayTeamUserIds)
                    if (request.pogUserId !in scrimUserIds) {
                        throw PolicyException(
                            errorCode = ErrorCode.USER_IS_NOT_FOUND,
                            message = "POG 플레이어를 찾을 수 없습니다.(양팀 소속 플레이어 중에서 설정할 수 있습니다.)"
                        )
                    }
                    entity.setBy(userFinder.findById(request.pogUserId!!))
                }
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
