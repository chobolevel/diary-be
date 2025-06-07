package com.diary.api.diaries

import com.diary.api.controller.v1.common.diaries.DiaryController
import com.diary.api.dto.common.PaginationResponseDto
import com.diary.api.dto.common.ResultResponseDto
import com.diary.api.dto.diaries.CreateDiaryRequestDto
import com.diary.api.dto.diaries.DiaryResponseDto
import com.diary.api.dto.diaries.UpdateDiaryRequestDto
import com.diary.api.dto.users.points.AddUserPointRequestDto
import com.diary.api.service.diaries.DiaryService
import com.diary.api.service.users.UserPointService
import com.diary.api.users.DummyUser
import com.diary.api.users.points.DummyUserPoint
import com.diary.domain.dto.Pagination
import com.diary.domain.entity.diaries.Diary
import com.diary.domain.entity.diaries.DiaryQueryFilter
import com.diary.domain.entity.users.User
import com.diary.domain.entity.users.points.UserPoint
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
@DisplayName("일기 컨트롤러 단위 테스트")
class DiaryControllerTest {

    private val dummyUser: User = DummyUser.toEntity()
    private val dummyUserToken: UsernamePasswordAuthenticationToken = DummyUser.toToken()

    private val dummyDiary: Diary = DummyDiary.toEntity()
    private val dummyDiaryResponse: DiaryResponseDto = DummyDiary.toResponseDto()

    @Mock
    private lateinit var service: DiaryService

    @Mock
    private lateinit var userPointService: UserPointService

    @InjectMocks
    private lateinit var controller: DiaryController

    @Test
    fun `일기 등록`() {
        // given
        val dummyUserPoint: UserPoint = DummyUserPoint.toEntity()
        val dummyUserId: ID = dummyUser.id
        val dummyDiaryId: ID = dummyDiary.id
        val request: CreateDiaryRequestDto = DummyDiary.toCreateRequestDto()
        val addUserPointRequest: AddUserPointRequestDto = AddUserPointRequestDto(
            amount = 200,
            reason = "일기 작성 완료! 200포인트 지급"
        )
        `when`(
            service.create(
                userId = dummyUserId,
                request = request
            )
        ).thenReturn(dummyDiaryId)
        `when`(
            userPointService.addPoint(
                userId = dummyUserId,
                request = addUserPointRequest
            )
        ).thenReturn(dummyUserPoint.id)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.create(
            principal = dummyUserToken,
            request = request
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.CREATED)
        assertThat(result.body?.data).isEqualTo(dummyDiaryId)
    }

    @Test
    fun `일기 목록 조회`() {
        // given
        val queryFilter = DiaryQueryFilter(
            writerId = null,
            weatherId = null,
            emotionId = null,
            title = null,
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
            service.getDiaries(
                queryFilter = queryFilter,
                pagination = pagination,
                orderTypes = emptyList()
            )
        ).thenReturn(paginationResponse)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.getDiaries(
            writerId = null,
            weatherId = null,
            emotionId = null,
            title = null,
            page = null,
            size = null,
            orderTypes = null
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(paginationResponse)
    }

    @Test
    fun `일기 단건 조회`() {
        // given
        val dummyDiaryId: ID = dummyDiary.id
        `when`(
            service.getDiary(
                diaryId = dummyDiaryId
            )
        ).thenReturn(dummyDiaryResponse)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.getDiary(diaryId = dummyDiaryId)

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(dummyDiaryResponse)
    }

    @Test
    fun `일기 정보 수정`() {
        // given
        val dummyUserId: ID = dummyUser.id
        val dummyDiaryId: ID = dummyDiary.id
        val request: UpdateDiaryRequestDto = DummyDiary.toUpdateRequestDto()
        `when`(
            service.update(
                userId = dummyUserId,
                diaryId = dummyDiaryId,
                request = request
            )
        ).thenReturn(dummyDiaryId)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.update(
            principal = dummyUserToken,
            diaryId = dummyDiaryId,
            request = request
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(dummyDiaryId)
    }

    @Test
    fun `일기 삭제`() {
        // given
        val dummyUserId: ID = dummyUser.id
        val dummyDiaryId: ID = dummyDiary.id
        `when`(
            service.delete(
                userId = dummyUserId,
                diaryId = dummyDiaryId
            )
        ).thenReturn(true)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.delete(
            principal = dummyUserToken,
            diaryId = dummyDiaryId,
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(true)
    }
}
