package com.diary.domain.entity.emotions

import com.diary.domain.dto.Pagination
import com.diary.domain.entity.emotions.QEmotion.emotion
import com.diary.domain.exception.DataNotFoundException
import com.diary.domain.exception.ErrorCode
import com.diary.domain.type.ID
import com.querydsl.core.types.OrderSpecifier
import org.springframework.stereotype.Component
import kotlin.jvm.Throws

@Component
class EmotionRepositoryWrapper(
    private val repository: EmotionRepository,
    private val customRepository: EmotionCustomRepository
) {

    fun save(emotion: Emotion): Emotion {
        return repository.save(emotion)
    }

    fun search(
        queryFilter: EmotionQueryFilter,
        pagination: Pagination,
        orderTypes: List<EmotionOrderType>
    ): List<Emotion> {
        return customRepository.search(
            booleanExpressions = queryFilter.toBooleanExpressions(),
            pagination = pagination,
            orderSpecifiers = orderTypes.toOrderSpecifiers()
        )
    }

    fun count(
        queryFilter: EmotionQueryFilter,
    ): Long {
        return customRepository.count(booleanExpressions = queryFilter.toBooleanExpressions())
    }

    @Throws(DataNotFoundException::class)
    fun findById(id: ID): Emotion {
        return repository.findByIdAndDeletedFalse(id = id) ?: throw DataNotFoundException(
            errorCode = ErrorCode.EMOTION_NOT_FOUND,
            message = ErrorCode.EMOTION_NOT_FOUND.message
        )
    }

    private fun List<EmotionOrderType>.toOrderSpecifiers(): Array<OrderSpecifier<*>> {
        return this.map {
            when (it) {
                EmotionOrderType.CREATED_AT_ASC -> emotion.createdAt.asc()
                EmotionOrderType.CREATED_AT_DESC -> emotion.createdAt.desc()
            }
        }.toTypedArray()
    }
}
