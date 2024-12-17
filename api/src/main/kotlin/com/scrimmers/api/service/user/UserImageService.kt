package com.scrimmers.api.service.user

import com.scrimmers.api.dto.user.image.CreateUserImageRequestDto
import com.scrimmers.api.dto.user.image.UpdateUserImageRequestDto
import com.scrimmers.api.service.user.converter.UserImageConverter
import com.scrimmers.api.service.user.updater.UserImageUpdater
import com.scrimmers.api.service.user.validator.UserImageValidator
import com.scrimmers.domain.entity.user.UserFinder
import com.scrimmers.domain.entity.user.image.UserImageFinder
import com.scrimmers.domain.entity.user.image.UserImageRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserImageService(
    private val repository: UserImageRepository,
    private val finder: UserImageFinder,
    private val userFinder: UserFinder,
    private val converter: UserImageConverter,
    private val validator: UserImageValidator,
    private val updater: UserImageUpdater
) {

    @Transactional
    fun create(userId: String, request: CreateUserImageRequestDto): String {
        val user = userFinder.findById(userId)
        val userImage = converter.convert(request).also {
            it.setBy(user)
        }
        return repository.save(userImage).id
    }

    @Transactional
    fun update(userId: String, userImageId: String, request: UpdateUserImageRequestDto): String {
        validator.validate(request)
        val user = userFinder.findById(userId)
        val userImage = finder.findByIdAndUserId(
            id = userImageId,
            userId = user.id
        )
        updater.markAsUpdate(
            request = request,
            entity = userImage
        )
        return userImage.id
    }

    @Transactional
    fun delete(userId: String, userImageId: String): Boolean {
        val user = userFinder.findById(userId)
        val userImage = finder.findByIdAndUserId(
            id = userImageId,
            userId = user.id
        )
        userImage.delete()
        return true
    }
}
