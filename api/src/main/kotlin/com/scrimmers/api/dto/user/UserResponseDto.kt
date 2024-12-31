package com.scrimmers.api.dto.user

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.scrimmers.api.dto.team.TeamResponseDto
import com.scrimmers.api.dto.user.image.UserImageResponseDto
import com.scrimmers.api.dto.user.summoner.UserSummonerResponseDto
import com.scrimmers.domain.entity.user.UserGenderType
import com.scrimmers.domain.entity.user.UserLoginType
import com.scrimmers.domain.entity.user.UserPositionType
import com.scrimmers.domain.entity.user.UserRoleType
import java.time.LocalDate

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UserResponseDto(
    val id: String,
    val email: String,
    val loginType: UserLoginType,
    val nickname: String,
    val age: Int,
    val birth: LocalDate,
    val gender: UserGenderType,
    val mainPosition: UserPositionType,
    val subPosition: UserPositionType,
    val role: UserRoleType,
    val profileImage: UserImageResponseDto?,
    val createdAt: Long,
    val updatedAt: Long
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UserDetailResponseDto(
    val id: String,
    val team: TeamResponseDto?,
    val email: String,
    val loginType: UserLoginType,
    val nickname: String,
    val age: Int,
    val birth: LocalDate,
    val gender: UserGenderType,
    val mainPosition: UserPositionType,
    val subPosition: UserPositionType,
    val role: UserRoleType,
    val profileImage: UserImageResponseDto?,
    val summoners: List<UserSummonerResponseDto>,
    val createdAt: Long,
    val updatedAt: Long
)
