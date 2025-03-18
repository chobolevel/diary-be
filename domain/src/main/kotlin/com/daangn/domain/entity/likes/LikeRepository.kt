package com.daangn.domain.entity.likes

import org.springframework.data.jpa.repository.JpaRepository

interface LikeRepository : JpaRepository<Like, String> {

    fun findByUserIdAndTargetIdAndDeletedFalse(userId: String, targetId: String): Like?
}
