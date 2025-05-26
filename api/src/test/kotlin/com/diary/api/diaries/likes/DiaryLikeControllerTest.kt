package com.diary.api.diaries.likes

import com.diary.api.controller.v1.common.diaries.DiaryLikeController
import com.diary.api.diaries.DummyDiary
import com.diary.api.dto.common.PaginationResponseDto
import com.diary.api.dto.common.ResultResponseDto
import com.diary.api.dto.diaries.DiaryResponseDto
import com.diary.api.service.diaries.DiaryLikeService
import com.diary.api.users.DummyUser
import com.diary.domain.dto.Pagination
import com.diary.domain.entity.diaries.Diary
import com.diary.domain.entity.diaries.likes.DiaryLikeQueryFilter
import com.diary.domain.entity.users.User
import com.diary.domain.type.ID
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

@ExtendWith(MockitoExtension::class)
@DisplayName("일기 좋아요 컨트롤러 단위 테스트")
class DiaryLikeControllerTest {

    private val dummyUser: User = DummyUser.toEntity()
    private val dummyUserToken: UsernamePasswordAuthenticationToken = DummyUser.toToken()

    private val dummyDiary: Diary = DummyDiary.toEntity()
    private val dummyDiaryResponse: DiaryResponseDto = DummyDiary.toResponseDto()

    @Mock
    private lateinit var service: DiaryLikeService

    @InjectMocks
    private lateinit var controller: DiaryLikeController

    @Test
    fun `일기 좋아요 or 좋아요 취소`() {
        // given
        val dummyUserId: ID = dummyUser.id
        val dummyDiaryId: ID = dummyDiary.id
        `when`(
            service.likeOrDislike(
                userId = dummyUserId,
                diaryId = dummyDiaryId
            )
        ).thenReturn(true)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.likeOrDislike(
            principal = dummyUserToken,
            diaryId = dummyDiaryId
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(true)
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
        val dummyDiaryResponses: List<DiaryResponseDto> = listOf(dummyDiaryResponse)
        val paginationResponse = PaginationResponseDto(
            page = pagination.page,
            size = pagination.size,
            data = dummyDiaryResponses,
            totalCount = dummyDiaryResponses.size.toLong()
        )
        `when`(
            service.getDiaryLikes(
                queryFilter = queryFilter,
                pagination = pagination,
                orderTypes = emptyList()
            )
        ).thenReturn(paginationResponse)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.getDiaryLikes(
            diaryId = null,
            userId = null,
            page = null,
            size = null,
            orderTypes = null
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(paginationResponse)
    }
}
