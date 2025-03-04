package com.daangn.api.service.users.converter

import com.daangn.api.dto.users.regions.CreateUserRegionRequestDto
import com.daangn.api.dto.users.regions.UserRegionResponseDto
import com.daangn.domain.entity.users.regions.UserRegion
import io.hypersistence.tsid.TSID
import org.springframework.stereotype.Component

@Component
class UserRegionConverter {

    fun convert(request: CreateUserRegionRequestDto): UserRegion {
        return UserRegion(
            id = TSID.fast().toString(),
            latitude = request.latitude,
            longitude = request.longitude,
            region1depthName = request.region1depthName,
            region2depthName = request.region2depthName,
            region3depthName = request.region3depthName,
        )
    }

    fun convert(entity: UserRegion): UserRegionResponseDto {
        return UserRegionResponseDto(
            id = entity.id,
            latitude = entity.latitude,
            longitude = entity.longitude,
            region1depthName = entity.region1depthName,
            region2depthName = entity.region2depthName,
            region3depthName = entity.region3depthName,
            createdAt = entity.createdAt!!.toInstant().toEpochMilli(),
            updatedAt = entity.updatedAt!!.toInstant().toEpochMilli(),
        )
    }

    fun convert(entities: List<UserRegion>): List<UserRegionResponseDto> {
        return entities.map { convert(it) }
    }
}
