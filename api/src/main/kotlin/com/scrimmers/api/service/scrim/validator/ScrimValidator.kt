package com.scrimmers.api.service.scrim.validator

import com.scrimmers.api.dto.scrim.CreateScrimRequestDto
import com.scrimmers.api.dto.scrim.UpdateScrimRequestDto
import com.scrimmers.domain.entity.team.scrim.ScrimUpdateMask
import com.scrimmers.domain.exception.ErrorCode
import com.scrimmers.domain.exception.ParameterInvalidException
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class ScrimValidator {

    fun validate(request: CreateScrimRequestDto) {
        if (request.startedAt.isBefore(LocalDateTime.now())) {
            throw ParameterInvalidException(
                errorCode = ErrorCode.PARAMETER_INVALID,
                message = "스크림 시작 일자는 현재 시각 이후만 가능합니다."
            )
        }
    }

    fun validate(request: UpdateScrimRequestDto) {
        request.updateMask.forEach {
            when (it) {
                ScrimUpdateMask.TYPE -> {
                    if (request.type == null) {
                        throw ParameterInvalidException(
                            errorCode = ErrorCode.PARAMETER_INVALID,
                            message = "변경할 스크림 타입이 유효하지 않습니다.(BO_1, BO_3, BO_5)"
                        )
                    }
                }

                ScrimUpdateMask.NAME -> {
                    if (request.name.isNullOrEmpty()) {
                        throw ParameterInvalidException(
                            errorCode = ErrorCode.PARAMETER_INVALID,
                            message = "변경할 스크림명이 유효하지 않습니다."
                        )
                    }
                }

                ScrimUpdateMask.STARTED_AT -> {
                    if (request.startedAt == null || request.startedAt.isBefore(LocalDateTime.now())) {
                        throw ParameterInvalidException(
                            errorCode = ErrorCode.PARAMETER_INVALID,
                            message = "변경할 스크림 시작 일자가 유효하지 않습니다.(현재 시각 이후만 가능)"
                        )
                    }
                }
            }
        }
    }
}
