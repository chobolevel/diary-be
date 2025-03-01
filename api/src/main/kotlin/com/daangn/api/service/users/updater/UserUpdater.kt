package com.daangn.api.service.users.updater

import com.daangn.api.dto.users.UpdateUserRequestDto
import com.daangn.domain.entity.users.User
import com.daangn.domain.entity.users.UserUpdateMask
import org.springframework.stereotype.Component

@Component
class UserUpdater {

    fun markAsUpdate(
        request: UpdateUserRequestDto,
        entity: User
    ): User {
        request.updateMask.forEach {
            when (it) {
                UserUpdateMask.NICKNAME -> entity.nickname = request.nickname!!
            }
        }
        return entity
    }
}
