package com.scrimmers.api.service.user.converter

import com.scrimmers.api.dto.user.summoner.CreateUserSummonerRequestDto
import com.scrimmers.api.dto.user.summoner.UserSummonerResponseDto
import com.scrimmers.domain.entity.user.summoner.SummonerRank
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
            summonerIconId = request.summonerIconId,
            summonerSoloRank = request.summonerSoloRank ?: SummonerRank.NONE,
            summonerFlexRank = request.summonerFlexRank ?: SummonerRank.NONE,
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
            summonerIconId = entity.summonerIconId,
            isUnRanked = entity.summonerSoloRank == SummonerRank.NONE && entity.summonerFlexRank == SummonerRank.NONE,
            summonerSoloRank = entity.summonerSoloRank,
            summonerFlexRank = entity.summonerFlexRank,
            createdAt = entity.createdAt!!.toInstant().toEpochMilli(),
            updatedAt = entity.updatedAt!!.toInstant().toEpochMilli()
        )
    }
}
