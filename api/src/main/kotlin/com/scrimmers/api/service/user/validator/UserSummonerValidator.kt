package com.scrimmers.api.service.user.validator

import com.scrimmers.api.dto.user.summoner.UpdateUserSummonerRequestDto
import com.scrimmers.domain.entity.user.summoner.SummonerRank
import com.scrimmers.domain.entity.user.summoner.UserSummonerUpdateMask
import com.scrimmers.domain.exception.ErrorCode
import com.scrimmers.domain.exception.ParameterInvalidException
import org.springframework.stereotype.Component

@Component
class UserSummonerValidator {

    @Throws(ParameterInvalidException::class)
    fun validate(request: UpdateUserSummonerRequestDto) {
        request.updateMask.forEach {
            when (it) {
                UserSummonerUpdateMask.SUMMONER_SOLO_RANK -> {
                    if (request.summonerSoloRank == null) {
                        throw ParameterInvalidException(
                            errorCode = ErrorCode.PARAMETER_INVALID,
                            message = "변경할 솔로랭크 티어(${SummonerRank.values().joinToString(", ")})가 유효하지 않습니다."
                        )
                    }
                }

                UserSummonerUpdateMask.SUMMONER_FLEX_RANK -> {
                    if (request.summonerFlexRank == null) {
                        throw ParameterInvalidException(
                            errorCode = ErrorCode.PARAMETER_INVALID,
                            message = "변경할 솔로랭크 티어(${SummonerRank.values().joinToString(", ")})가 유효하지 않습니다."
                        )
                    }
                }
            }
        }
    }
}
