package com.diary.api.service.emotions

import com.diary.api.dto.common.PaginationResponseDto
import com.diary.api.dto.emotions.CreateEmotionRequestDto
import com.diary.api.dto.emotions.EmotionResponseDto
import com.diary.api.dto.emotions.UpdateEmotionRequestDto
import com.diary.api.service.emotions.converter.EmotionConverter
import com.diary.api.service.emotions.updater.EmotionUpdater
import com.diary.domain.dto.Pagination
import com.diary.domain.entity.emotions.Emotion
import com.diary.domain.entity.emotions.EmotionOrderType
import com.diary.domain.entity.emotions.EmotionQueryFilter
import com.diary.domain.entity.emotions.EmotionRepositoryWrapper
import com.diary.domain.type.ID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EmotionService(
    private val repositoryWrapper: EmotionRepositoryWrapper,
    private val converter: EmotionConverter,
    private val updater: EmotionUpdater
) {

    @Transactional
    fun create(request: CreateEmotionRequestDto): ID {
        val emotion: Emotion = converter.convert(request = request)
        return repositoryWrapper.save(emotion = emotion).id
    }

    fun getEmotions(
        queryFilter: EmotionQueryFilter,
        pagination: Pagination,
        orderTypes: List<EmotionOrderType>
    ): PaginationResponseDto {
        val emotions: List<Emotion> = repositoryWrapper.search(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes
        )
        val totalCount = repositoryWrapper.count(queryFilter = queryFilter)
        return PaginationResponseDto(
            page = pagination.page,
            size = pagination.size,
            data = converter.convert(entities = emotions),
            totalCount = totalCount
        )
    }

    fun getEmotion(emotionId: ID): EmotionResponseDto {
        val emotion: Emotion = repositoryWrapper.findById(id = emotionId)
        return converter.convert(entity = emotion)
    }

    @Transactional
    fun update(
        emotionId: ID,
        request: UpdateEmotionRequestDto,
    ): ID {
        val emotion: Emotion = repositoryWrapper.findById(id = emotionId)
        updater.markAsUpdate(
            request = request,
            entity = emotion
        )
        return emotionId
    }

    @Transactional
    fun delete(
        emotionId: ID
    ): Boolean {
        val emotion: Emotion = repositoryWrapper.findById(id = emotionId)
        emotion.delete()
        return true
    }
}
