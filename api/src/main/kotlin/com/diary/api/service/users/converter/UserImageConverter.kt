package com.diary.api.service.users.converter

import com.diary.api.dto.users.images.CreateUserImageRequestDto
import com.diary.domain.entity.users.images.UserImage
import io.hypersistence.tsid.TSID
import org.springframework.stereotype.Component

@Component
class UserImageConverter {

    fun convert(request: CreateUserImageRequestDto): UserImage {
        return UserImage(
            id = TSID.fast().toString(),
            name = request.name,
            width = request.width,
            height = request.height,
            url = request.url,
            order = request.order,
        )
    }
}
