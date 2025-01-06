package com.scrimmers.api.service.team.validator

import com.scrimmers.api.dto.team.CreateTeamRequestDto
import com.scrimmers.api.dto.team.UpdateTeamRequestDto
import com.scrimmers.domain.entity.team.TeamFinder
import com.scrimmers.domain.entity.team.TeamUpdateMask
import com.scrimmers.domain.exception.ErrorCode
import com.scrimmers.domain.exception.ParameterInvalidException
import com.scrimmers.domain.exception.PolicyException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class TeamValidator(
    private val teamFinder: TeamFinder
) {

    fun validate(request: CreateTeamRequestDto) {
        validateTeamNameDuplicated(request.name)
        if (request.maxHeadCount < 5) {
            throw ParameterInvalidException(
                errorCode = ErrorCode.PARAMETER_INVALID,
                status = HttpStatus.BAD_REQUEST,
                message = "팀 최대 인원수는 최소 5명 이상이어야 합니다."
            )
        }
    }

    fun validate(request: UpdateTeamRequestDto) {
        request.updateMask.forEach {
            when (it) {
                TeamUpdateMask.NAME -> {
                    if (request.name.isNullOrEmpty()) {
                        throw ParameterInvalidException(
                            errorCode = ErrorCode.PARAMETER_INVALID,
                            status = HttpStatus.BAD_REQUEST,
                            message = "변경할 팀명이 유효하지 않습니다.",
                        )
                    }
                    validateTeamNameDuplicated(request.name)
                }

                TeamUpdateMask.DESCRIPTION -> {
                    if (request.description.isNullOrEmpty()) {
                        throw ParameterInvalidException(
                            errorCode = ErrorCode.PARAMETER_INVALID,
                            status = HttpStatus.BAD_REQUEST,
                            message = "변경한 팀 설명이 유효하지 않습니다."
                        )
                    }
                }

                TeamUpdateMask.MAX_HEAD_COUNT -> {
                    if (request.maxHeadCount == null || request.maxHeadCount < 5) {
                        throw ParameterInvalidException(
                            errorCode = ErrorCode.PARAMETER_INVALID,
                            status = HttpStatus.BAD_REQUEST,
                            message = "변경할 팀 최대 인원수가 유효하지 않습니다.(최소 5이상)"
                        )
                    }
                }
            }
        }
    }

    @Throws(PolicyException::class)
    private fun validateTeamNameDuplicated(teamName: String) {
        val isExists = teamFinder.existsByName(teamName.lowercase())
        if (isExists) {
            throw PolicyException(
                errorCode = ErrorCode.PARAMETER_INVALID,
                message = "이미 존재하는 팀명입니다."
            )
        }
    }
}
