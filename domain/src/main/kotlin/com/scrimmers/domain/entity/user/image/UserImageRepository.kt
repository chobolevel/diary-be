package com.scrimmers.domain.entity.user.image

import org.springframework.data.jpa.repository.JpaRepository

interface UserImageRepository : JpaRepository<UserImage, String> {

    fun findByIdAndUserIdAndDeletedFalse(id: String, userId: String): UserImage?
}
