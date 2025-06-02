package com.diary.api.service.diaries

import com.diary.api.dto.diaries.images.CreateDiaryImageRequestDto
import com.diary.api.dto.diaries.images.UpdateDiaryImageRequestDto
import com.diary.api.service.diaries.converter.DiaryImageConverter
import com.diary.api.service.diaries.updater.DiaryImageUpdater
import com.diary.api.service.diaries.validator.DiaryImageValidator
import com.diary.domain.entity.diaries.Diary
import com.diary.domain.entity.diaries.DiaryRepositoryWrapper
import com.diary.domain.entity.diaries.images.DiaryImage
import com.diary.domain.entity.diaries.images.DiaryImageRepositoryWrapper
import com.diary.domain.type.ID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DiaryImageService(
    private val repositoryWrapper: DiaryImageRepositoryWrapper,
    private val diaryRepositoryWrapper: DiaryRepositoryWrapper,
    private val converter: DiaryImageConverter,
    private val updater: DiaryImageUpdater,
    private val validator: DiaryImageValidator
) {

    @Transactional
    fun create(
        diaryId: ID,
        request: CreateDiaryImageRequestDto
    ): ID {
        validator.validate(request = request)
        val diaryImage: DiaryImage = converter.convert(request = request).also { diaryImage ->
            // mapping diary
            val diary: Diary = diaryRepositoryWrapper.findById(id = diaryId)
            diaryImage.set(diary = diary)
        }
        return repositoryWrapper.save(diaryImage = diaryImage).id
    }

    @Transactional
    fun update(
        diaryId: ID,
        diaryImageId: ID,
        request: UpdateDiaryImageRequestDto
    ): ID {
        validator.validate(request = request)
        val diaryImage: DiaryImage = repositoryWrapper.findByIdAndDiaryId(
            id = diaryImageId,
            diaryId = diaryId
        )
        updater.markAsUpdate(
            request = request,
            entity = diaryImage
        )
        return diaryImageId
    }

    @Transactional
    fun delete(
        diaryId: ID,
        diaryImageId: ID
    ): Boolean {
        val diaryImage: DiaryImage = repositoryWrapper.findByIdAndDiaryId(
            id = diaryImageId,
            diaryId = diaryId
        )
        diaryImage.delete()
        return true
    }
}
