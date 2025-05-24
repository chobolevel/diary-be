package com.diary.api.diaries

import com.diary.api.dto.common.PaginationResponseDto
import com.diary.api.dto.diaries.CreateDiaryRequestDto
import com.diary.api.dto.diaries.DiaryResponseDto
import com.diary.api.dto.diaries.UpdateDiaryRequestDto
import com.diary.api.emotions.DummyEmotion
import com.diary.api.service.diaries.DiaryService
import com.diary.api.service.diaries.converter.DiaryConverter
import com.diary.api.service.diaries.updater.DiaryUpdater
import com.diary.api.service.diaries.validator.DiaryValidator
import com.diary.api.users.DummyUser
import com.diary.api.weathers.DummyWeather
import com.diary.domain.dto.Pagination
import com.diary.domain.entity.diaries.Diary
import com.diary.domain.entity.diaries.DiaryQueryFilter
import com.diary.domain.entity.diaries.DiaryRepositoryWrapper
import com.diary.domain.entity.emotions.Emotion
import com.diary.domain.entity.emotions.EmotionRepositoryWrapper
import com.diary.domain.entity.users.User
import com.diary.domain.entity.users.UserRepositoryWrapper
import com.diary.domain.entity.weathers.Weather
import com.diary.domain.entity.weathers.WeatherRepositoryWrapper
import com.diary.domain.type.ID
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
@DisplayName("일기 서비스 로직 단위 테스트")
class DiaryServiceTest {

    private val dummyDiary: Diary = DummyDiary.toEntity()
    private val dummyDiaryResponse: DiaryResponseDto = DummyDiary.toResponseDto()

    private val dummyUser: User = DummyUser.toEntity()

    private val dummyWeather: Weather = DummyWeather.toEntity()

    private val dummyEmotion: Emotion = DummyEmotion.toEntity()

    @Mock
    private lateinit var repositoryWrapper: DiaryRepositoryWrapper

    @Mock
    private lateinit var userRepositoryWrapper: UserRepositoryWrapper

    @Mock
    private lateinit var weatherRepositoryWrapper: WeatherRepositoryWrapper

    @Mock
    private lateinit var emotionRepositoryWrapper: EmotionRepositoryWrapper

    @Mock
    private lateinit var converter: DiaryConverter

    @Mock
    private lateinit var updater: DiaryUpdater

    @Mock
    private lateinit var validator: DiaryValidator

    @InjectMocks
    private lateinit var service: DiaryService

    @Test
    fun `일기 등록`() {
        // given
        val dummyUserId: ID = dummyUser.id
        val request: CreateDiaryRequestDto = DummyDiary.toCreateRequestDto()
        `when`(converter.convert(request = request)).thenReturn(dummyDiary)
        `when`(userRepositoryWrapper.findById(id = dummyUserId)).thenReturn(dummyUser)
        `when`(weatherRepositoryWrapper.findById(id = request.weatherId)).thenReturn(dummyWeather)
        `when`(emotionRepositoryWrapper.findById(id = request.emotionId)).thenReturn(dummyEmotion)
        `when`(repositoryWrapper.save(diary = dummyDiary)).thenReturn(dummyDiary)

        // when
        val result: ID = service.create(
            userId = dummyUserId,
            request = request
        )

        // then
        assertThat(result).isEqualTo(dummyDiary.id)
    }

    @Test
    fun `일기 목록 조회`() {
        // given
        val queryFilter = DiaryQueryFilter(
            writerId = null,
            weatherId = null,
            emotionId = null,
            title = null
        )
        val pagination = Pagination(
            page = 1,
            size = 10
        )
        val dummyDiaries: List<Diary> = listOf(dummyDiary)
        val dummyDiaryResponses: List<DiaryResponseDto> = listOf(dummyDiaryResponse)
        `when`(
            repositoryWrapper.search(
                queryFilter = queryFilter,
                pagination = pagination,
                orderTypes = emptyList()
            )
        ).thenReturn(dummyDiaries)
        `when`(repositoryWrapper.count(queryFilter = queryFilter)).thenReturn(dummyDiaries.size.toLong())
        `when`(converter.convert(entities = dummyDiaries)).thenReturn(dummyDiaryResponses)

        // when
        val result: PaginationResponseDto = service.getDiaries(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = emptyList()
        )

        // then
        assertThat(result.page).isEqualTo(pagination.page)
        assertThat(result.size).isEqualTo(pagination.size)
        assertThat(result.data).isEqualTo(dummyDiaryResponses)
        assertThat(result.totalCount).isEqualTo(dummyDiaryResponses.size.toLong())
    }

    @Test
    fun `일기 단건 조회`() {
        // given
        val dummyDiaryId: ID = dummyDiary.id
        `when`(repositoryWrapper.findById(id = dummyDiaryId)).thenReturn(dummyDiary)
        `when`(converter.convert(entity = dummyDiary)).thenReturn(dummyDiaryResponse)

        // when
        val result: DiaryResponseDto = service.getDiary(diaryId = dummyDiaryId)

        // then
        assertThat(result).isEqualTo(dummyDiaryResponse)
    }

    @Test
    fun `일기 정보 수정`() {
        // given
        val dummyUserId: ID = dummyUser.id
        val dummyDiaryId: ID = dummyDiary.id
        val request: UpdateDiaryRequestDto = DummyDiary.toUpdateRequestDto()
        `when`(repositoryWrapper.findById(id = dummyDiaryId)).thenReturn(dummyDiary)
        `when`(
            updater.markAsUpdate(
                request = request,
                entity = dummyDiary
            )
        ).thenReturn(dummyDiary)

        // when
        val result: ID = service.update(
            userId = dummyUserId,
            diaryId = dummyDiaryId,
            request = request
        )

        // then
        assertThat(result).isEqualTo(dummyDiaryId)
    }

    @Test
    fun `일기 정보 삭제`() {
        // given
        val dummyUserId: ID = dummyUser.id
        val dummyDiaryId: ID = dummyDiary.id
        `when`(repositoryWrapper.findById(id = dummyDiaryId)).thenReturn(dummyDiary)

        // when
        val result: Boolean = service.delete(
            userId = dummyUserId,
            diaryId = dummyDiaryId
        )

        // then
        assertThat(result).isTrue()
    }
}
