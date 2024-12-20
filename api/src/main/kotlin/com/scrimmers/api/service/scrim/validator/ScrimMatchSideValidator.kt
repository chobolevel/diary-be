package com.scrimmers.api.service.scrim.validator

import com.scrimmers.api.dto.scrim.match.side.UpdateScrimMatchSideRequestDto
import com.scrimmers.domain.entity.team.scrim.match.side.ScrimMatchSideUpdateMask
import com.scrimmers.domain.exception.ErrorCode
import com.scrimmers.domain.exception.ParameterInvalidException
import org.springframework.stereotype.Component

@Component
class ScrimMatchSideValidator {

    fun validate(request: UpdateScrimMatchSideRequestDto) {
        request.updateMask.forEach {
            when (it) {
                ScrimMatchSideUpdateMask.KILL_SCORE -> {
                    if (request.killScore == null) {
                        throw ParameterInvalidException(
                            errorCode = ErrorCode.PARAMETER_INVALID,
                            message = "변경할 킬 스코어가 유효하지 않습니다."
                        )
                    }
                }

                ScrimMatchSideUpdateMask.TOTAL_GOLD -> {
                    if (request.totalGold == null) {
                        throw ParameterInvalidException(
                            errorCode = ErrorCode.PARAMETER_INVALID,
                            message = "변경할 총 골드가 유효하지 않습니다."
                        )
                    }
                }
            }
        }
    }
}
