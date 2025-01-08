package com.scrimmers.api.service.team.updater

import com.scrimmers.api.dto.team.UpdateTeamRequestDto
import com.scrimmers.domain.entity.team.Team
import com.scrimmers.domain.entity.team.TeamUpdateMask
import org.springframework.stereotype.Component

@Component
class TeamUpdater {

    fun markAsUpdate(request: UpdateTeamRequestDto, entity: Team): Team {
        request.updateMask.forEach {
            when (it) {
                TeamUpdateMask.NAME -> entity.name = request.name!!
                TeamUpdateMask.DESCRIPTION -> entity.description = request.description!!
                TeamUpdateMask.MAX_HEAD_COUNT -> {
                    if (request.maxHeadCount!! <= entity.headCount) {
                        entity.maxHeadCount = entity.headCount
                    } else {
                        entity.maxHeadCount = request.maxHeadCount
                    }
                }
            }
        }
        return entity
    }
}
