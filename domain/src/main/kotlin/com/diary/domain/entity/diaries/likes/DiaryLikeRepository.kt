package com.diary.domain.entity.diaries.likes

import com.diary.domain.type.ID
import org.springframework.data.jpa.repository.JpaRepository

interface DiaryLikeRepository : JpaRepository<DiaryLike, ID> {

    fun findByIdAndDeletedFalse(id: ID): DiaryLike?
}
