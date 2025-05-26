package com.diary.api.diaries.likes

import com.diary.api.diaries.DummyDiary
import com.diary.api.dto.common.PaginationResponseDto
import com.diary.api.dto.diaries.likes.DiaryLikeResponseDto
import com.diary.api.service.diaries.DiaryLikeService
import com.diary.api.service.diaries.converter.DiaryLikeConverter
import com.diary.api.users.DummyUser
import com.diary.domain.dto.Pagination
import com.diary.domain.entity.diaries.Diary
import com.diary.domain.entity.diaries.DiaryRepositoryWrapper
import com.diary.domain.entity.diaries.likes.DiaryLike
import com.diary.domain.entity.diaries.likes.DiaryLikeQueryFilter
import com.diary.domain.entity.diaries.likes.DiaryLikeRepositoryWrapper
import com.diary.domain.entity.users.User
import com.diary.domain.entity.users.UserRepositoryWrapper
import com.diary.domain.type.ID
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import reactor.core.publisher.Mono.`when`

@ExtendWith(MockitoExtension::class)
@DisplayName("일기 좋아요 서비스 로직 단위 테스트")
class DiaryLikeServiceTest {

    private val dummyDiary: Diary = DummyDiary.toEntity()

    private val dummyUser: User = DummyUser.toEntity()

    private val dummyDiaryLike: DiaryLike = DummyDiaryLike.toEntity()
    private val dummyDiaryLikeResponse: DiaryLikeResponseDto = DummyDiaryLike.toResponseDto()

    @Mock
    private lateinit var repositoryWrapper: DiaryLikeRepositoryWrapper

    @Mock
    private lateinit var diaryRepositoryWrapper: DiaryRepositoryWrapper

    @Mock
    private lateinit var userRepositoryWrapper: UserRepositoryWrapper

    @Mock
    private lateinit var converter: DiaryLikeConverter

    @InjectMocks
    private lateinit var service: DiaryLikeService

    @Test
    fun `일기 좋아요`() {
        // given
        val dummyUserId: ID = dummyUser.id
        val dummyDiaryId: ID = dummyDiary.id
        `when`(
            repositoryWrapper.findByDiaryIdAndUserIdOrNull(
                diaryId = dummyDiaryId,
                userId = dummyUserId
            )
        ).thenReturn(null)
        `when`(converter.convert()).thenReturn(dummyDiaryLike)
        `when`(diaryRepositoryWrapper.findById(id = dummyDiaryId)).thenReturn(dummyDiary)
        `when`(userRepositoryWrapper.findById(id = dummyUserId)).thenReturn(dummyUser)
        `when`(repositoryWrapper.save(diaryLike = dummyDiaryLike)).thenReturn(dummyDiaryLike)

        // when
        val result: Boolean = service.likeOrDislike(
            userId = dummyUserId,
            diaryId = dummyDiaryId
        )

        // then
        assertThat(result).isTrue()
    }

    @Test
    fun `일기 좋아요 취소`() {
        // given
        val dummyUserId: ID = dummyUser.id
        val dummyDiaryId: ID = dummyDiary.id
        `when`(
            repositoryWrapper.findByDiaryIdAndUserIdOrNull(
                diaryId = dummyDiaryId,
                userId = dummyUserId
            )
        ).thenReturn(dummyDiaryLike)

        // when
        val result: Boolean = service.likeOrDislike(
            userId = dummyUserId,
            diaryId = dummyDiaryId
        )

        // then
        assertThat(result).isTrue()
    }

    @Test
    fun `일기 좋아요 목록 조회`() {
        // given
        val queryFilter = DiaryLikeQueryFilter(
            diaryId = null,
            userId = null
        )
        val pagination = Pagination(
            page = 1,
            size = 10
        )
        val dummyDiaryLikes: List<DiaryLike> = listOf(dummyDiaryLike)
        val dummyDiaryLikeResponses: List<DiaryLikeResponseDto> = listOf(dummyDiaryLikeResponse)
        `when`(
            repositoryWrapper.search(
                queryFilter = queryFilter,
                pagination = pagination,
                orderTypes = emptyList()
            )
        ).thenReturn(dummyDiaryLikes)
        `when`(repositoryWrapper.count(queryFilter = queryFilter)).thenReturn(dummyDiaryLikes.size.toLong())
        `when`(converter.convert(entities = dummyDiaryLikes)).thenReturn(dummyDiaryLikeResponses)

        // when
        val result: PaginationResponseDto = service.getDiaryLikes(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = emptyList()
        )

        // then
        assertThat(result.page).isEqualTo(pagination.page)
        assertThat(result.size).isEqualTo(pagination.size)
        assertThat(result.data).isEqualTo(dummyDiaryLikeResponses)
        assertThat(result.totalCount).isEqualTo(dummyDiaryLikeResponses.size.toLong())
    }
}
