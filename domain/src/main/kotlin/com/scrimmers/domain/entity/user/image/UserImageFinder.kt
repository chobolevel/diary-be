package com.scrimmers.domain.entity.user.image

import com.scrimmers.domain.exception.DataNotFoundException
import com.scrimmers.domain.exception.ErrorCode
import org.springframework.stereotype.Component

@Component
class UserImageFinder(
    private val repository: UserImageRepository
) {

    fun findByIdAndUserId(id: String, userId: String): UserImage {
        return repository.findByIdAndUserIdAndDeletedFalse(
            id = id,
            userId = userId
        ) ?: throw DataNotFoundException(
            errorCode = ErrorCode.USER_IMAGE_IS_NOT_FOUND,
            message = ErrorCode.USER_IMAGE_IS_NOT_FOUND.desc
        )
    }
}
