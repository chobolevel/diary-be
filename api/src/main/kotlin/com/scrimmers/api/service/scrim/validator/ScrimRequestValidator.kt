package com.scrimmers.api.service.scrim.validator

import com.scrimmers.api.dto.scrim.request.CreateScrimRequestRequestDto
import com.scrimmers.api.dto.scrim.request.UpdateScrimRequestRequestDto
import com.scrimmers.domain.entity.team.scrim.request.ScrimRequestUpdateMask
import com.scrimmers.domain.exception.ErrorCode
import com.scrimmers.domain.exception.ParameterInvalidException
import org.springframework.stereotype.Component
import kotlin.jvm.Throws

@Component
class ScrimRequestValidator {

    @Throws(ParameterInvalidException::class)
    fun validate(request: CreateScrimRequestRequestDto) {
        if (request.comment.length < 10) {
            throw ParameterInvalidException(
                errorCode = ErrorCode.PARAMETER_INVALID,
                message = "스크림 요청 인사말은 최소 10글자 이상이어야 합니다."
            )
        }
    }

    @Throws(ParameterInvalidException::class)
    fun validate(request: UpdateScrimRequestRequestDto) {
        request.updateMask.forEach {
            when (it) {
                ScrimRequestUpdateMask.COMMENT -> {
                    if (request.comment.isNullOrEmpty() || request.comment.length < 10) {
                        throw ParameterInvalidException(
                            errorCode = ErrorCode.PARAMETER_INVALID,
                            message = "변경할 스크링 요청 인사말이 유효하지 않습니다.(최소 10글자 이상)"
                        )
                    }
                }
            }
        }
    }
}
