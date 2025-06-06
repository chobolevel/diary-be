package com.diary.domain.entity.users.images

import com.diary.domain.exception.DataNotFoundException
import com.diary.domain.exception.ErrorCode
import com.diary.domain.type.ID
import org.springframework.stereotype.Component

@Component
class UserImageRepositoryWrapper(
    private val repository: UserImageRepository
) {

    fun save(userImage: UserImage): UserImage {
        return repository.save(userImage)
    }

    @Throws(DataNotFoundException::class)
    fun findByIdAndUserId(
        id: ID,
        userId: ID
    ): UserImage {
        return repository.findByIdAndUserIdAndDeletedFalse(
            id = id,
            userId = userId
        ) ?: throw DataNotFoundException(
            errorCode = ErrorCode.USER_IMAGE_NOT_FOUND,
            message = ErrorCode.USER_IMAGE_NOT_FOUND.message
        )
    }
}
