package com.scrimmers.api.service.user.converter

import com.scrimmers.api.dto.user.image.CreateUserImageRequestDto
import com.scrimmers.api.dto.user.image.UserImageResponseDto
import com.scrimmers.domain.entity.user.image.UserImage
import io.hypersistence.tsid.TSID
import org.springframework.stereotype.Component

@Component
class UserImageConverter {

    fun convert(request: CreateUserImageRequestDto): UserImage {
        return UserImage(
            id = TSID.fast().toString(),
            type = request.type,
            name = request.name,
            url = request.url
        )
    }

    fun convert(entity: UserImage?): UserImageResponseDto? {
        if (entity == null) {
            return null
        }
        return UserImageResponseDto(
            id = entity.id,
            type = entity.type,
            name = entity.name,
            url = entity.url,
            createdAt = entity.createdAt!!.toInstant().toEpochMilli(),
            updatedAt = entity.updatedAt!!.toInstant().toEpochMilli()
        )
    }
}
