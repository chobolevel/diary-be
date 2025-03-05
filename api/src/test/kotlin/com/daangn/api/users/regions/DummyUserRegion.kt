package com.daangn.api.users.regions

import com.daangn.api.dto.users.regions.CreateUserRegionRequestDto
import com.daangn.api.dto.users.regions.UpdateUserRegionRequestDto
import com.daangn.api.dto.users.regions.UserRegionResponseDto
import com.daangn.domain.entity.users.regions.UserRegion
import com.daangn.domain.entity.users.regions.UserRegionUpdateMask
import io.hypersistence.tsid.TSID

object DummyUserRegion {
    private val id: String = TSID.fast().toString()
    private val latitude: Double = 35.2602308946252
    private val longitude: Double = 128.636698019516
    private val region1depthName: String = "경남"
    private val region2depthName: String = "창원시 의창구"
    private val region3depthName: String = "도계동"
    private val createdAt: Long = 0L
    private val updatedAt: Long = 0L

    fun toCreateRequestDto(): CreateUserRegionRequestDto {
        return createRequest
    }
    fun toEntity(): UserRegion {
        return userRegion
    }
    fun toResponseDto(): UserRegionResponseDto {
        return userRegionResponse
    }
    fun toUpdateRequestDto(): UpdateUserRegionRequestDto {
        return updateRequest
    }

    private val createRequest: CreateUserRegionRequestDto by lazy {
        CreateUserRegionRequestDto(
            latitude = latitude,
            longitude = longitude,
            firstDepthName = region1depthName,
            secondDepthName = region2depthName,
            thirdDepthName = region3depthName,
        )
    }
    private val userRegion: UserRegion by lazy {
        UserRegion(
            id = id,
            latitude = latitude,
            longitude = longitude,
            region1depthName = region1depthName,
            region2depthName = region2depthName,
            region3depthName = region3depthName,
        )
    }
    private val userRegionResponse: UserRegionResponseDto by lazy {
        UserRegionResponseDto(
            id = id,
            latitude = latitude,
            longitude = longitude,
            firstDepthName = region1depthName,
            secondDepthName = region2depthName,
            thirdDepthName = region3depthName,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }
    private val updateRequest: UpdateUserRegionRequestDto by lazy {
        UpdateUserRegionRequestDto(
            latitude = 10.0,
            longitude = null,
            firstDepthName = null,
            secondDepthName = null,
            thirdDepthName = null,
            updateMask = listOf(
                UserRegionUpdateMask.LATITUDE
            )
        )
    }
}
