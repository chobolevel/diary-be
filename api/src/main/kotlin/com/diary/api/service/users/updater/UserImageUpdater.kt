package com.diary.api.service.users.updater

import com.diary.api.dto.users.images.UpdateUserImageRequestDto
import com.diary.domain.entity.users.images.UserImage
import com.diary.domain.entity.users.images.UserImageUpdateMask
import org.springframework.stereotype.Component

@Component
class UserImageUpdater {

    fun markAsUpdate(
        request: UpdateUserImageRequestDto,
        entity: UserImage
    ): UserImage {
        request.updateMask.forEach {
            when (it) {
                UserImageUpdateMask.NAME -> entity.name = request.name!!
                UserImageUpdateMask.WIDTH -> entity.width = request.width!!
                UserImageUpdateMask.HEIGHT -> entity.height = request.height!!
                UserImageUpdateMask.URL -> entity.url = request.url!!
                UserImageUpdateMask.ORDER -> entity.order = request.order!!
            }
        }
        return entity
    }
}
