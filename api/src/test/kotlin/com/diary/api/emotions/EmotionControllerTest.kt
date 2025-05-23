package com.diary.api.emotions

import com.diary.api.controller.v1.common.emotions.EmotionController
import com.diary.api.dto.common.PaginationResponseDto
import com.diary.api.dto.common.ResultResponseDto
import com.diary.api.dto.emotions.EmotionResponseDto
import com.diary.api.service.emotions.EmotionService
import com.diary.domain.dto.Pagination
import com.diary.domain.entity.emotions.Emotion
import com.diary.domain.entity.emotions.EmotionQueryFilter
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

@ExtendWith(MockitoExtension::class)
@DisplayName("감정 컨트롤러 단위 테스트")
class EmotionControllerTest {

    private val dummyEmotion: Emotion = DummyEmotion.toEntity()
    private val dummyEmotionResponse: EmotionResponseDto = DummyEmotion.toResponseDto()

    @Mock
    private lateinit var service: EmotionService

    @InjectMocks
    private lateinit var controller: EmotionController

    @Test
    fun `감정 목록 조회`() {
        // given
        val queryFilter = EmotionQueryFilter(name = null)
        val pagination = Pagination(
            page = 1,
            size = 10
        )
        val dummyEmotionResponses: List<EmotionResponseDto> = listOf(dummyEmotionResponse)
        val paginationResponse = PaginationResponseDto(
            page = pagination.page,
            size = pagination.size,
            data = dummyEmotionResponses,
            totalCount = dummyEmotionResponses.size.toLong()
        )
        `when`(
            service.getEmotions(
                queryFilter = queryFilter,
                pagination = pagination,
                orderTypes = emptyList()
            )
        ).thenReturn(paginationResponse)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.getEmotions(
            name = null,
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
    fun `감정 단건 조회`() {
        // given
        val dummyEmotionId: ID = dummyEmotion.id
        `when`(service.getEmotion(emotionId = dummyEmotionId)).thenReturn(dummyEmotionResponse)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.getEmotion(emotionId = dummyEmotionId)

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(dummyEmotionResponse)
    }
}
