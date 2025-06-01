package com.diary.domain.entity.diaries.images

import com.diary.domain.type.ID
import org.springframework.data.jpa.repository.JpaRepository

interface DiaryImageRepository : JpaRepository<DiaryImage, ID> {

    fun findByIdAndDiaryIdAndDeletedFalse(
        id: ID,
        diaryId: ID
    ): DiaryImage?
}
