package com.diary.domain.entity.diaries

import com.diary.domain.type.ID
import org.springframework.data.jpa.repository.JpaRepository

interface DiaryRepository : JpaRepository<Diary, ID> {

    fun findByIdAndDeletedFalse(id: ID): Diary?
}
