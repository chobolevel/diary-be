package com.diary.domain.entity.users.images

import org.springframework.stereotype.Component

@Component
class UserImageRepositoryWrapper(
    private val repository: UserImageRepository
) {

    fun save(userImage: UserImage): UserImage {
        return repository.save(userImage)
    }
}
