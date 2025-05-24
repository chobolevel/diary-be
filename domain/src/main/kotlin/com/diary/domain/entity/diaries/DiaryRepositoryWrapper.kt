package com.diary.domain.entity.diaries

import com.diary.domain.dto.Pagination
import com.diary.domain.entity.diaries.QDiary.diary
import com.diary.domain.exception.DataNotFoundException
import com.diary.domain.exception.ErrorCode
import com.diary.domain.type.ID
import com.querydsl.core.types.OrderSpecifier
import org.springframework.stereotype.Component
import kotlin.jvm.Throws

@Component
class DiaryRepositoryWrapper(
    private val repository: DiaryRepository,
    private val customRepository: DiaryCustomRepository
) {

    fun save(diary: Diary): Diary {
        return repository.save(diary)
    }

    fun search(
        queryFilter: DiaryQueryFilter,
        pagination: Pagination,
        orderTypes: List<DiaryOrderType>
    ): List<Diary> {
        return customRepository.search(
            booleanExpressions = queryFilter.toBooleanExpressions(),
            pagination = pagination,
            orderSpecifiers = orderTypes.toOrderSpecifiers()
        )
    }

    fun count(queryFilter: DiaryQueryFilter): Long {
        return customRepository.count(booleanExpressions = queryFilter.toBooleanExpressions())
    }

    @Throws(DataNotFoundException::class)
    fun findById(id: ID): Diary {
        return repository.findByIdAndDeletedFalse(id = id) ?: throw DataNotFoundException(
            errorCode = ErrorCode.DIARY_NOT_FOUND,
            message = ErrorCode.DIARY_NOT_FOUND.message
        )
    }

    private fun List<DiaryOrderType>.toOrderSpecifiers(): Array<OrderSpecifier<*>> {
        return this.map {
            when (it) {
                DiaryOrderType.CREATED_AT_ASC -> diary.createdAt.asc()
                DiaryOrderType.CREATED_AT_DESC -> diary.createdAt.desc()
            }
        }.toTypedArray()
    }
}
