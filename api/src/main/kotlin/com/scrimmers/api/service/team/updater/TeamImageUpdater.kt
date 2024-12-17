package com.scrimmers.api.service.team.updater

import com.scrimmers.api.dto.team.image.UpdateTeamImageRequestDto
import com.scrimmers.domain.entity.team.image.TeamImage
import com.scrimmers.domain.entity.team.image.TeamImageUpdateMask
import org.springframework.stereotype.Component

@Component
class TeamImageUpdater {

    fun markAsUpdate(request: UpdateTeamImageRequestDto, entity: TeamImage): TeamImage {
        request.updateMask.forEach {
            when (it) {
                TeamImageUpdateMask.TYPE -> entity.type = request.type!!
                TeamImageUpdateMask.NAME -> entity.name = request.name!!
                TeamImageUpdateMask.URL -> entity.url = request.url!!
            }
        }
        return entity
    }
}
