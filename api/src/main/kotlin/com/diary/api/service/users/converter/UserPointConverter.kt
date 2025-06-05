package com.diary.api.service.users.converter

import com.diary.api.dto.users.points.AddUserPointRequestDto
import com.diary.api.dto.users.points.SubUserPointRequestDto
import com.diary.api.dto.users.points.UserPointResponseDto
import com.diary.domain.entity.users.points.UserPoint
import com.diary.domain.entity.users.points.UserPointType
import io.hypersistence.tsid.TSID
import org.springframework.stereotype.Component

@Component
class UserPointConverter {

    fun convert(request: AddUserPointRequestDto): UserPoint {
        return UserPoint(
            id = TSID.fast().toString(),
            type = UserPointType.ADD,
            amount = request.amount,
            reason = request.reason
        )
    }

    fun convert(request: SubUserPointRequestDto): UserPoint {
        return UserPoint(
            id = TSID.fast().toString(),
            type = UserPointType.SUB,
            amount = request.amount,
            reason = request.reason
        )
    }

    fun convert(entity: UserPoint): UserPointResponseDto {
        return UserPointResponseDto(
            id = entity.id,
            type = entity.type,
            typeLabel = entity.type.desc,
            amount = entity.amount,
            reason = entity.reason,
            createdAt = entity.createdAt!!.toInstant().toEpochMilli(),
            updatedAt = entity.updatedAt!!.toInstant().toEpochMilli()
        )
    }

    fun convert(entities: List<UserPoint>): List<UserPointResponseDto> {
        return entities.map { convert(it) }
    }
}
