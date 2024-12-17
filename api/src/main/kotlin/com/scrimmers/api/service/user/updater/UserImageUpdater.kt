package com.scrimmers.api.service.user.updater

import com.scrimmers.api.dto.user.image.UpdateUserImageRequestDto
import com.scrimmers.domain.entity.user.image.UserImage
import com.scrimmers.domain.entity.user.image.UserImageUpdateMask
import org.springframework.stereotype.Component

@Component
class UserImageUpdater {

    fun markAsUpdate(request: UpdateUserImageRequestDto, entity: UserImage): UserImage {
        request.updateMask.forEach {
            when (it) {
                UserImageUpdateMask.TYPE -> entity.type = request.type!!
                UserImageUpdateMask.NAME -> entity.name = request.name!!
                UserImageUpdateMask.URL -> entity.url = request.url!!
            }
        }
        return entity
    }
}
