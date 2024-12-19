package com.scrimmers.api.service.user.updater

import com.scrimmers.api.dto.user.UpdateUserRequestDto
import com.scrimmers.domain.entity.user.User
import com.scrimmers.domain.entity.user.UserUpdateMask
import org.springframework.stereotype.Component

@Component
class UserUpdater {

    fun markAsUpdate(request: UpdateUserRequestDto, entity: User): User {
        request.updateMask.forEach {
            when (it) {
                UserUpdateMask.NICKNAME -> entity.nickname = request.nickname!!
                UserUpdateMask.PHONE -> entity.phone = request.phone!!
                UserUpdateMask.BIRTH -> entity.birth = request.birth!!
                UserUpdateMask.GENDER -> entity.gender = request.gender!!
                UserUpdateMask.MAIN_POSITION -> entity.mainPosition = request.mainPosition!!
                UserUpdateMask.SUB_POSITION -> entity.subPosition = request.subPosition!!
            }
        }
        return entity
    }
}
