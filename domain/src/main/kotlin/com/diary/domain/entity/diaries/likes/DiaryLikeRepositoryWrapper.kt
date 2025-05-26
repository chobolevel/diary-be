package com.diary.domain.entity.diaries.likes

import com.diary.domain.dto.Pagination
import com.diary.domain.entity.diaries.likes.QDiaryLike.diaryLike
import com.diary.domain.exception.DataNotFoundException
import com.diary.domain.exception.ErrorCode
import com.diary.domain.type.ID
import com.querydsl.core.types.OrderSpecifier
import org.springframework.stereotype.Component
import kotlin.jvm.Throws

@Component
class DiaryLikeRepositoryWrapper(
    private val repository: DiaryLikeRepository,
    private val customRepository: DiaryLikeCustomRepository
) {

    fun save(diaryLike: DiaryLike): DiaryLike {
        return repository.save(diaryLike)
    }

    fun search(
        queryFilter: DiaryLikeQueryFilter,
        pagination: Pagination,
        orderTypes: List<DiaryLikeOrderType>
    ): List<DiaryLike> {
        return customRepository.search(
            booleanExpressions = queryFilter.toBooleanExpressions(),
            pagination = pagination,
            orderSpecifiers = orderTypes.toOrderSpecifiers()
        )
    }

    fun count(
        queryFilter: DiaryLikeQueryFilter,
    ): Long {
        return customRepository.count(booleanExpressions = queryFilter.toBooleanExpressions())
    }

    @Throws(DataNotFoundException::class)
    fun findById(id: ID): DiaryLike {
        return repository.findByIdAndDeletedFalse(id = id) ?: throw DataNotFoundException(
            errorCode = ErrorCode.DIARY_LIKE_NOT_FOUND,
            message = ErrorCode.DIARY_LIKE_NOT_FOUND.message
        )
    }

    fun findByDiaryIdAndUserIdOrNull(
        diaryId: ID,
        userId: ID
    ): DiaryLike? {
        return repository.findByDiaryIdAndUserIdAndDeletedFalse(
            diaryId = diaryId,
            userId = userId
        )
    }

    fun countByDiaryId(diaryId: ID): Long {
        return repository.countByDiaryIdAndDeletedFalse(diaryId = diaryId)
    }

    private fun List<DiaryLikeOrderType>.toOrderSpecifiers(): Array<OrderSpecifier<*>> {
        return this.map {
            when (it) {
                DiaryLikeOrderType.CREATED_AT_ASC -> diaryLike.createdAt.asc()
                DiaryLikeOrderType.CREATED_AT_DESC -> diaryLike.createdAt.desc()
            }
        }.toTypedArray()
    }
}
