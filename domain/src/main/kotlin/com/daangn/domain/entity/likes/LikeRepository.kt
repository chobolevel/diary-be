package com.daangn.domain.entity.likes

import org.springframework.data.jpa.repository.JpaRepository

interface LikeRepository: JpaRepository<Like, String> {

    fun existsByUserIdAndTargetIdAndDeletedFalse(userId: String, targetId: String): Like?
}
