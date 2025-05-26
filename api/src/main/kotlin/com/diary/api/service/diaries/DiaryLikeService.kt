package com.diary.api.service.diaries

import com.diary.api.dto.common.PaginationResponseDto
import com.diary.api.service.diaries.converter.DiaryLikeConverter
import com.diary.domain.dto.Pagination
import com.diary.domain.entity.diaries.Diary
import com.diary.domain.entity.diaries.DiaryRepositoryWrapper
import com.diary.domain.entity.diaries.likes.DiaryLike
import com.diary.domain.entity.diaries.likes.DiaryLikeOrderType
import com.diary.domain.entity.diaries.likes.DiaryLikeQueryFilter
import com.diary.domain.entity.diaries.likes.DiaryLikeRepositoryWrapper
import com.diary.domain.entity.users.User
import com.diary.domain.entity.users.UserRepositoryWrapper
import com.diary.domain.type.ID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DiaryLikeService(
    private val repositoryWrapper: DiaryLikeRepositoryWrapper,
    private val diaryRepositoryWrapper: DiaryRepositoryWrapper,
    private val userRepositoryWrapper: UserRepositoryWrapper,
    private val converter: DiaryLikeConverter
) {

    @Transactional
    fun likeOrDislike(userId: ID, diaryId: ID): Boolean {
        val diaryLike: DiaryLike? = repositoryWrapper.findByDiaryIdAndUserIdOrNull(
            diaryId = diaryId,
            userId = userId
        )
        when (diaryLike == null) {
            true -> {
                // like
                val diaryLike: DiaryLike = converter.convert().also { diaryLike ->
                    // mapping diary
                    val diary: Diary = diaryRepositoryWrapper.findById(id = diaryId)
                    diaryLike.set(diary = diary)

                    // mapping user
                    val user: User = userRepositoryWrapper.findById(id = userId)
                    diaryLike.set(user = user)
                }
                repositoryWrapper.save(diaryLike = diaryLike)
            }
            false -> {
                // dislike
                diaryLike.delete()
            }
        }
        return true
    }

    @Transactional(readOnly = true)
    fun getDiaryLikes(
        queryFilter: DiaryLikeQueryFilter,
        pagination: Pagination,
        orderTypes: List<DiaryLikeOrderType>
    ): PaginationResponseDto {
        val diaryLikes: List<DiaryLike> = repositoryWrapper.search(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes
        )
        val totalCount: Long = repositoryWrapper.count(queryFilter = queryFilter)
        return PaginationResponseDto(
            page = pagination.page,
            size = pagination.size,
            data = converter.convert(entities = diaryLikes),
            totalCount = totalCount
        )
    }
}
