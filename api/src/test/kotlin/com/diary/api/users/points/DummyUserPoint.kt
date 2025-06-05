package com.diary.api.users.points

import com.diary.api.dto.users.points.AddUserPointRequestDto
import com.diary.api.dto.users.points.SubUserPointRequestDto
import com.diary.api.dto.users.points.UserPointResponseDto
import com.diary.domain.entity.users.points.UserPoint
import com.diary.domain.entity.users.points.UserPointType
import com.diary.domain.type.ID

object DummyUserPoint {
    private val id: ID = "0KH4WDSJA2CHB"
    private val type: UserPointType = UserPointType.ADD
    private val amount: Int = 2000
    private val reason: String = "회원 가입 환영 포인트"
    private val createdAt: Long = 0L
    private val updatedAt: Long = 0L

    private val dummyUserPoint: UserPoint by lazy {
        UserPoint(
            id = id,
            type = type,
            amount = amount,
            reason = reason
        )
    }
    private val dummyUserPointResponse: UserPointResponseDto by lazy {
        UserPointResponseDto(
            id = id,
            type = type,
            typeLabel = type.desc,
            amount = amount,
            reason = reason,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }
    private val addUserPointRequest: AddUserPointRequestDto by lazy {
        AddUserPointRequestDto(
            amount = amount,
            reason = reason
        )
    }
    private val subUserPointRequest: SubUserPointRequestDto by lazy {
        SubUserPointRequestDto(
            amount = amount,
            reason = "$reason 회수"
        )
    }

    fun toEntity(): UserPoint {
        return dummyUserPoint
    }
    fun toResponseDto(): UserPointResponseDto {
        return dummyUserPointResponse
    }
    fun toAddUserPointRequestDto(): AddUserPointRequestDto {
        return addUserPointRequest
    }
    fun toSubUserPointRequestDto(): SubUserPointRequestDto {
        return subUserPointRequest
    }
}
