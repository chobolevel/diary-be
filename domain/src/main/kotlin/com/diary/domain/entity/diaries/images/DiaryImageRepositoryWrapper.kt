package com.diary.domain.entity.diaries.images

import com.diary.domain.exception.DataNotFoundException
import com.diary.domain.exception.ErrorCode
import com.diary.domain.type.ID
import org.springframework.context.annotation.ComponentScan
import kotlin.jvm.Throws

@ComponentScan
class DiaryImageRepositoryWrapper(
    private val repository: DiaryImageRepository
) {

    fun save(diaryImage: DiaryImage): DiaryImage {
        return repository.save(diaryImage)
    }

    @Throws(DataNotFoundException::class)
    fun findByIdAndDiaryId(
        id: ID,
        diaryId: ID
    ): DiaryImage {
        return repository.findByIdAndDiaryIdAndDeletedFalse(
            id = id,
            diaryId = diaryId
        ) ?: throw DataNotFoundException(
            errorCode = ErrorCode.DIARY_IMAGE_NOT_FOUND,
            message = ErrorCode.DIARY_IMAGE_NOT_FOUND.message
        )
    }
}
