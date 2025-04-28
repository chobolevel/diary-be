package com.diary.api.service.users.updater

import com.diary.api.dto.users.UpdateUserRequestDto
import com.diary.domain.entity.users.User
import com.diary.domain.entity.users.UserUpdateMask
import org.springframework.stereotype.Component

@Component
class UserUpdater {

    fun markAsUpdate(request: UpdateUserRequestDto, entity: User): User {
        request.updateMask.forEach {
            when (it) {
                UserUpdateMask.NICKNAME -> entity.nickname = request.nickname!!
                UserUpdateMask.SCOPE -> entity.scope = request.scope!!
            }
        }
        return entity
    }
}
