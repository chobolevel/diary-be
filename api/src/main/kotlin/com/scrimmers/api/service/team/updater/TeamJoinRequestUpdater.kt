package com.scrimmers.api.service.team.updater

import com.scrimmers.api.dto.team.join.UpdateTeamJoinRequestDto
import com.scrimmers.domain.entity.team.join.TeamJoinRequest
import com.scrimmers.domain.entity.team.join.TeamJoinRequestUpdateMask
import org.springframework.stereotype.Component

@Component
class TeamJoinRequestUpdater {

    fun markAsUpdate(request: UpdateTeamJoinRequestDto, entity: TeamJoinRequest): TeamJoinRequest {
        request.updateMask.forEach {
            when (it) {
                TeamJoinRequestUpdateMask.COMMENT -> entity.comment = request.comment!!
            }
        }
        return entity
    }
}
