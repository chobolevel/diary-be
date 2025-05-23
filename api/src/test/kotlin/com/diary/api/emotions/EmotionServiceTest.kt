package com.diary.api.emotions

import com.diary.api.dto.common.PaginationResponseDto
import com.diary.api.dto.emotions.CreateEmotionRequestDto
import com.diary.api.dto.emotions.EmotionResponseDto
import com.diary.api.dto.emotions.UpdateEmotionRequestDto
import com.diary.api.service.emotions.EmotionService
import com.diary.api.service.emotions.converter.EmotionConverter
import com.diary.api.service.emotions.updater.EmotionUpdater
import com.diary.domain.dto.Pagination
import com.diary.domain.entity.emotions.Emotion
import com.diary.domain.entity.emotions.EmotionQueryFilter
import com.diary.domain.entity.emotions.EmotionRepositoryWrapper
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
@DisplayName("감정 서비스 단위 테스트")
class EmotionServiceTest {

    private val dummyEmotion: Emotion = DummyEmotion.toEntity()
    private val dummyEmotionResponse: EmotionResponseDto = DummyEmotion.toResponseDto()

    @Mock
    private lateinit var repositoryWrapper: EmotionRepositoryWrapper

    @Mock
    private lateinit var converter: EmotionConverter

    @Mock
    private lateinit var updater: EmotionUpdater

    @InjectMocks
    private lateinit var service: EmotionService

    @Test
    fun `감정 등록`() {
        // given
        val request: CreateEmotionRequestDto = DummyEmotion.toCreateRequestDto()
        `when`(converter.convert(request = request)).thenReturn(dummyEmotion)
        `when`(repositoryWrapper.save(emotion = dummyEmotion)).thenReturn(dummyEmotion)

        // when
        val result: ID = service.create(request = request)

        // then
        assertThat(result).isEqualTo(dummyEmotion.id)
    }

    @Test
    fun `감정 목록 조회`() {
        // given
        val queryFilter = EmotionQueryFilter(
            name = null,
        )
        val pagination = Pagination(
            page = 1,
            size = 10
        )
        val dummyEmotions: List<Emotion> = listOf(dummyEmotion)
        val dummyEmotionResponses: List<EmotionResponseDto> = listOf(dummyEmotionResponse)
        `when`(
            repositoryWrapper.search(
                queryFilter = queryFilter,
                pagination = pagination,
                orderTypes = emptyList()
            )
        ).thenReturn(dummyEmotions)
        `when`(
            repositoryWrapper.count(
                queryFilter = queryFilter
            )
        ).thenReturn(dummyEmotions.size.toLong())
        `when`(converter.convert(entities = dummyEmotions)).thenReturn(dummyEmotionResponses)

        // when
        val result: PaginationResponseDto = service.getEmotions(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = emptyList()
        )

        // then
        assertThat(result.page).isEqualTo(pagination.page)
        assertThat(result.size).isEqualTo(pagination.size)
        assertThat(result.data).isEqualTo(dummyEmotionResponses)
        assertThat(result.totalCount).isEqualTo(dummyEmotionResponses.size.toLong())
    }

    @Test
    fun `감정 단건 조회`() {
        // given
        val dummyEmotionId: ID = dummyEmotion.id
        `when`(repositoryWrapper.findById(id = dummyEmotionId)).thenReturn(dummyEmotion)
        `when`(converter.convert(entity = dummyEmotion)).thenReturn(dummyEmotionResponse)

        // when
        val result: EmotionResponseDto = service.getEmotion(emotionId = dummyEmotionId)

        // then
        assertThat(result).isEqualTo(dummyEmotionResponse)
    }

    @Test
    fun `감정 정보 수정`() {
        // given
        val dummyEmotionId: ID = dummyEmotion.id
        val request: UpdateEmotionRequestDto = DummyEmotion.toUpdateRequestDto()
        `when`(repositoryWrapper.findById(id = dummyEmotionId)).thenReturn(dummyEmotion)
        `when`(
            updater.markAsUpdate(
                request = request,
                entity = dummyEmotion
            )
        ).thenReturn(dummyEmotion)

        // when
        val result: ID = service.update(
            emotionId = dummyEmotionId,
            request = request
        )

        // then
        assertThat(result).isEqualTo(dummyEmotion.id)
    }

    @Test
    fun `감정 정보 삭제`() {
        // given
        val dummyEmotionId: ID = dummyEmotion.id
        `when`(repositoryWrapper.findById(id = dummyEmotionId)).thenReturn(dummyEmotion)

        // when
        val result: Boolean = service.delete(emotionId = dummyEmotionId)

        // then
        assertThat(result).isTrue()
    }
}
