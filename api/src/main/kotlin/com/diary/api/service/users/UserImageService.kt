package com.diary.api.service.users

import com.diary.api.dto.users.images.CreateUserImageRequestDto
import com.diary.api.dto.users.images.UpdateUserImageRequestDto
import com.diary.api.service.users.converter.UserImageConverter
import com.diary.api.service.users.updater.UserImageUpdater
import com.diary.domain.entity.users.User
import com.diary.domain.entity.users.UserRepositoryWrapper
import com.diary.domain.entity.users.images.UserImage
import com.diary.domain.entity.users.images.UserImageRepositoryWrapper
import com.diary.domain.type.ID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserImageService(
    private val repositoryWrapper: UserImageRepositoryWrapper,
    private val userRepositoryWrapper: UserRepositoryWrapper,
    private val converter: UserImageConverter,
    private val updater: UserImageUpdater
) {

    @Transactional
    fun create(
        userId: ID,
        request: CreateUserImageRequestDto
    ): ID {
        val userImage: UserImage = converter.convert(request = request).also { userImage ->
            // mapping user
            val user: User = userRepositoryWrapper.findById(id = userId)
            userImage.set(user = user)
        }
        return repositoryWrapper.save(userImage = userImage).id
    }

    @Transactional
    fun update(
        userId: ID,
        userImageId: ID,
        request: UpdateUserImageRequestDto
    ): ID {
        val userImage: UserImage = repositoryWrapper.findByIdAndUserId(
            id = userImageId,
            userId = userId
        )
        updater.markAsUpdate(
            request = request,
            entity = userImage
        )
        return userImageId
    }

    @Transactional
    fun delete(
        userId: ID,
        userImageId: ID
    ): Boolean {
        val userImage: UserImage = repositoryWrapper.findByIdAndUserId(
            id = userImageId,
            userId = userId
        )
        userImage.delete()
        return true
    }
}
