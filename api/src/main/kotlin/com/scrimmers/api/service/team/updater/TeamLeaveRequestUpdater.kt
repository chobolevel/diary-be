package com.scrimmers.api.service.team.updater

import com.scrimmers.api.dto.team.leave.UpdateTeamLeaveRequestDto
import com.scrimmers.domain.entity.team.leave.TeamLeaveRequest
import com.scrimmers.domain.entity.team.leave.TeamLeaveRequestUpdateMask
import org.springframework.stereotype.Component

@Component
class TeamLeaveRequestUpdater {

    fun markAsUpdate(request: UpdateTeamLeaveRequestDto, entity: TeamLeaveRequest): TeamLeaveRequest {
        request.updateMask.forEach {
            when (it) {
                TeamLeaveRequestUpdateMask.COMMENT -> entity.comment = request.comment!!
            }
        }
        return entity
    }
}
