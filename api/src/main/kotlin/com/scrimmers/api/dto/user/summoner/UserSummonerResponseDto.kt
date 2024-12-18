package com.scrimmers.api.dto.user.summoner

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UserSummonerResponseDto(
    val id: String,
    val summonerId: String,
    val summonerName: String,
    val summonerTag: String,
    val summonerFullName: String,
    val summonerLevel: Int,
    val summonerIconUrl: String,
    val createdAt: Long,
    val updatedAt: Long
)
