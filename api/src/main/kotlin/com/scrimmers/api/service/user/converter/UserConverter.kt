package com.scrimmers.api.service.user.converter

import com.scrimmers.api.dto.user.CreateUserRequestDto
import com.scrimmers.api.dto.user.UserDetailResponseDto
import com.scrimmers.api.dto.user.UserResponseDto
import com.scrimmers.api.service.team.converter.TeamConverter
import com.scrimmers.domain.entity.user.User
import com.scrimmers.domain.entity.user.UserLoginType
import com.scrimmers.domain.entity.user.UserRoleType
import io.hypersistence.tsid.TSID
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.Period
import kotlin.math.floor

@Component
class UserConverter(
    private val passwordEncoder: BCryptPasswordEncoder,
    private val userImageConverter: UserImageConverter,
    private val userSummonerConverter: UserSummonerConverter,
    private val teamConverter: TeamConverter
) {

    fun convert(request: CreateUserRequestDto): User {
        return when (request.loginType) {
            UserLoginType.GENERAL -> {
                User(
                    id = TSID.fast().toString(),
                    email = request.email,
                    password = passwordEncoder.encode(request.password),
                    socialId = null,
                    loginType = request.loginType,
                    nickname = request.nickname,
                    birth = request.birth,
                    gender = request.gender,
                    role = UserRoleType.ROLE_USER,
                )
            }

            else -> {
                User(
                    id = TSID.fast().toString(),
                    email = request.email,
                    password = null,
                    socialId = request.socialId,
                    loginType = request.loginType,
                    nickname = request.nickname,
                    birth = request.birth,
                    gender = request.gender,
                    role = UserRoleType.ROLE_USER,
                )
            }
        }
    }

    fun convert(entity: User): UserResponseDto {
        return UserResponseDto(
            id = entity.id,
            email = entity.email,
            loginType = entity.loginType,
            nickname = entity.nickname,
            ageRange = (floor(Period.between(entity.birth, LocalDate.now()).years / 10.0) * 10).toInt(),
            gender = entity.gender,
            mainPosition = entity.mainPosition,
            subPosition = entity.subPosition,
            role = entity.role,
            profileImage = userImageConverter.convert(entity.userImage),
            createdAt = entity.createdAt!!.toInstant().toEpochMilli(),
            updatedAt = entity.updatedAt!!.toInstant().toEpochMilli()
        )
    }

    fun convertForDetail(entity: User): UserDetailResponseDto {
        return UserDetailResponseDto(
            id = entity.id,
            team = entity.team?.let { teamConverter.convert(it) },
            email = entity.email,
            loginType = entity.loginType,
            nickname = entity.nickname,
            ageRange = (floor(Period.between(entity.birth, LocalDate.now()).years / 10.0) * 10).toInt(),
            gender = entity.gender,
            mainPosition = entity.mainPosition,
            subPosition = entity.subPosition,
            role = entity.role,
            profileImage = userImageConverter.convert(entity.userImage),
            summoners = entity.summoners.map { userSummonerConverter.convert(it) },
            createdAt = entity.createdAt!!.toInstant().toEpochMilli(),
            updatedAt = entity.updatedAt!!.toInstant().toEpochMilli()
        )
    }
}
