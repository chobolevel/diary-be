package com.diary.api.service.diaries

import com.diary.api.dto.common.PaginationResponseDto
import com.diary.api.dto.diaries.CreateDiaryRequestDto
import com.diary.api.dto.diaries.DiaryResponseDto
import com.diary.api.dto.diaries.UpdateDiaryRequestDto
import com.diary.api.service.diaries.converter.DiaryConverter
import com.diary.api.service.diaries.updater.DiaryUpdater
import com.diary.api.service.diaries.validator.DiaryValidator
import com.diary.api.util.validateWriter
import com.diary.domain.dto.Pagination
import com.diary.domain.entity.diaries.Diary
import com.diary.domain.entity.diaries.DiaryOrderType
import com.diary.domain.entity.diaries.DiaryQueryFilter
import com.diary.domain.entity.diaries.DiaryRepositoryWrapper
import com.diary.domain.entity.emotions.Emotion
import com.diary.domain.entity.emotions.EmotionRepositoryWrapper
import com.diary.domain.entity.users.User
import com.diary.domain.entity.users.UserRepositoryWrapper
import com.diary.domain.entity.weathers.Weather
import com.diary.domain.entity.weathers.WeatherRepositoryWrapper
import com.diary.domain.type.ID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DiaryService(
    private val repositoryWrapper: DiaryRepositoryWrapper,
    private val userRepositoryWrapper: UserRepositoryWrapper,
    private val weatherRepositoryWrapper: WeatherRepositoryWrapper,
    private val emotionRepositoryWrapper: EmotionRepositoryWrapper,
    private val converter: DiaryConverter,
    private val updater: DiaryUpdater,
    private val validator: DiaryValidator
) {

    @Transactional
    fun create(
        userId: ID,
        request: CreateDiaryRequestDto
    ): ID {
        validator.validate(request = request)
        val diary: Diary = converter.convert(request = request).also { diary ->
            // mapping writer
            val user: User = userRepositoryWrapper.findById(id = userId)
            diary.set(writer = user)

            // mapping weather
            val weather: Weather = weatherRepositoryWrapper.findById(id = request.weatherId)
            diary.set(weather = weather)

            // mapping emotion
            val emotion: Emotion = emotionRepositoryWrapper.findById(id = request.emotionId)
            diary.set(emotion = emotion)
        }
        return repositoryWrapper.save(diary = diary).id
    }

    @Transactional(readOnly = true)
    fun getDiaries(
        queryFilter: DiaryQueryFilter,
        pagination: Pagination,
        orderTypes: List<DiaryOrderType>
    ): PaginationResponseDto {
        val diaries: List<Diary> = repositoryWrapper.search(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes
        )
        val totalCount: Long = repositoryWrapper.count(queryFilter = queryFilter)
        return PaginationResponseDto(
            page = pagination.page,
            size = pagination.size,
            data = converter.convert(entities = diaries),
            totalCount = totalCount
        )
    }

    @Transactional(readOnly = true)
    fun getDiary(diaryId: ID): DiaryResponseDto {
        val diary: Diary = repositoryWrapper.findById(id = diaryId)
        return converter.convert(entity = diary)
    }

    @Transactional
    fun update(
        userId: ID,
        diaryId: ID,
        request: UpdateDiaryRequestDto
    ): ID {
        validator.validate(request = request)
        val diary: Diary = repositoryWrapper.findById(id = diaryId)
        diary.validateWriter(userId = userId)
        updater.markAsUpdate(
            request = request,
            entity = diary
        )
        return diaryId
    }

    @Transactional
    fun delete(
        userId: ID,
        diaryId: ID
    ): Boolean {
        val diary: Diary = repositoryWrapper.findById(id = diaryId)
        diary.validateWriter(userId = userId)
        diary.delete()
        return true
    }
}
