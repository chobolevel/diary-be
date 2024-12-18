package com.scrimmers.api.dto.user.summoner

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.scrimmers.domain.entity.user.summoner.SummonerRank

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UserSummonerResponseDto(
    val id: String,
    val summonerId: String,
    val summonerName: String,
    val summonerTag: String,
    val summonerFullName: String,
    val summonerLevel: Int,
    val summonerIconUrl: String,
    val isUnRanked: Boolean,
    val summonerSoloRank: SummonerRank,
    val summonerFlexRank: SummonerRank,
    val createdAt: Long,
    val updatedAt: Long
)
