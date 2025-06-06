package com.diary.api.users.images

import com.diary.api.dto.users.images.CreateUserImageRequestDto
import com.diary.api.dto.users.images.UpdateUserImageRequestDto
import com.diary.api.dto.users.images.UserImageResponseDto
import com.diary.domain.entity.users.images.UserImage
import com.diary.domain.entity.users.images.UserImageUpdateMask
import com.diary.domain.type.ID

object DummyUserImage {
    private val id: ID = "0KH4WDSJA2CHB"
    private val name: String = "flow.jpeg"
    private val width: Int = 1000
    private val height: Int = 600
    private val url: String = "https://chobolevel.s3.ap-northeast-2.amazonaws.com/image/2025/05/26/9604d334-4fa3-4b17-8dd0-205ca309ed18.jpeg"
    private val order: Int = 1
    private val createdAt: Long = 0L
    private val updatedAt: Long = 0L

    private val dummyUserImage: UserImage by lazy {
        UserImage(
            id = id,
            name = name,
            width = width,
            height = height,
            url = url,
            order = order
        )
    }
    private val dummyUserImageResponse: UserImageResponseDto by lazy {
        UserImageResponseDto(
            id = id,
            name = name,
            width = width,
            height = height,
            url = url,
            order = order,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
    private val createRequest: CreateUserImageRequestDto by lazy {
        CreateUserImageRequestDto(
            name = name,
            width = width,
            height = height,
            url = url,
            order = order
        )
    }
    private val updateRequest: UpdateUserImageRequestDto by lazy {
        UpdateUserImageRequestDto(
            name = "test.jpeg",
            width = null,
            height = null,
            url = null,
            order = null,
            updateMask = listOf(UserImageUpdateMask.NAME)
        )
    }

    fun toEntity(): UserImage {
        return dummyUserImage
    }
    fun toResponseDto(): UserImageResponseDto {
        return dummyUserImageResponse
    }
    fun toCreateRequestDto(): CreateUserImageRequestDto {
        return createRequest
    }
    fun toUpdateRequestDto(): UpdateUserImageRequestDto {
        return updateRequest
    }
}
