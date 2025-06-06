package com.diary.domain.entity.users.images

import com.diary.domain.type.ID
import org.springframework.data.jpa.repository.JpaRepository

interface UserImageRepository : JpaRepository<UserImage, ID> {

    fun findByIdAndUserIdAndDeletedFalse(
        id: ID,
        userId: ID
    ): UserImage?
}
