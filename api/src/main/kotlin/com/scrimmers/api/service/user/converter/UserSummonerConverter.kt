package com.scrimmers.api.service.user.converter

import com.scrimmers.api.dto.user.summoner.CreateUserSummonerRequestDto
import com.scrimmers.api.dto.user.summoner.UserSummonerResponseDto
import com.scrimmers.domain.entity.user.summoner.UserSummoner
import io.hypersistence.tsid.TSID
import org.springframework.stereotype.Component

@Component
class UserSummonerConverter {

    fun convert(request: CreateUserSummonerRequestDto): UserSummoner {
        return UserSummoner(
            id = TSID.fast().toString(),
            summonerId = request.summonerId,
            summonerName = request.summonerName,
            summonerTag = request.summonerTag,
            summonerLevel = request.summonerLevel,
            summonerIconUrl = request.summonerIconUrl,
        )
    }

    fun convert(entity: UserSummoner): UserSummonerResponseDto {
        return UserSummonerResponseDto(
            id = entity.id,
            summonerId = entity.summonerId,
            summonerName = entity.summonerName,
            summonerTag = entity.summonerTag,
            summonerFullName = "${entity.summonerName}#${entity.summonerTag}",
            summonerLevel = entity.summonerLevel,
            summonerIconUrl = entity.summonerIconUrl,
            createdAt = entity.createdAt!!.toInstant().toEpochMilli(),
            updatedAt = entity.updatedAt!!.toInstant().toEpochMilli()
        )
    }
}
