package com.scrimmers.api.service.scrim.validator

import com.scrimmers.api.dto.scrim.match.UpdateScrimMatchRequestDto
import com.scrimmers.domain.entity.scrim.match.ScrimMatchUpdateMask
import com.scrimmers.domain.exception.ErrorCode
import com.scrimmers.domain.exception.ParameterInvalidException
import org.springframework.stereotype.Component

@Component
class ScrimMatchValidator(
    private val scrimMatchSideValidator: ScrimMatchSideValidator
) {

    fun validate(request: UpdateScrimMatchRequestDto) {
        request.updateMask.forEach {
            when (it) {
                ScrimMatchUpdateMask.WINNER_SIDE -> {
                    if (request.winnerSide == null) {
                        throw ParameterInvalidException(
                            errorCode = ErrorCode.PARAMETER_INVALID,
                            message = "변경할 승리 사이드가 유효하지 않습니다."
                        )
                    }
                }

                ScrimMatchUpdateMask.BLUE_TEAM -> {
                    if (request.blueTeam == null) {
                        throw ParameterInvalidException(
                            errorCode = ErrorCode.PARAMETER_INVALID,
                            message = "변경할 블루 사이드 정보가 유효하지 않습니다."
                        )
                    }
                    scrimMatchSideValidator.validate(request.blueTeam)
                }

                ScrimMatchUpdateMask.RED_TEAM -> {
                    if (request.redTeam == null) {
                        throw ParameterInvalidException(
                            errorCode = ErrorCode.PARAMETER_INVALID,
                            message = "변경할 레트 사이드 정보가 유효하지 않습니다."
                        )
                    }
                    scrimMatchSideValidator.validate(request.redTeam)
                }
            }
        }
    }
}
