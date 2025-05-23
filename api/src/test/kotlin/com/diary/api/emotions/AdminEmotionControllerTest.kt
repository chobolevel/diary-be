package com.diary.api.emotions

import com.diary.api.controller.v1.admin.emotions.AdminEmotionController
import com.diary.api.dto.common.ResultResponseDto
import com.diary.api.dto.emotions.CreateEmotionRequestDto
import com.diary.api.dto.emotions.UpdateEmotionRequestDto
import com.diary.api.service.emotions.EmotionService
import com.diary.domain.entity.emotions.Emotion
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
@DisplayName("관리자 감정 컨트롤러 단위 테스트")
class AdminEmotionControllerTest {

    private val dummyEmotion: Emotion = DummyEmotion.toEntity()

    @Mock
    private lateinit var service: EmotionService

    @InjectMocks
    private lateinit var controller: AdminEmotionController

    @Test
    fun `감정 등록`() {
        // given
        val dummyEmotionId: ID = dummyEmotion.id
        val request: CreateEmotionRequestDto = DummyEmotion.toCreateRequestDto()
        `when`(service.create(request = request)).thenReturn(dummyEmotionId)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.create(request = request)

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.CREATED)
        assertThat(result.body?.data).isEqualTo(dummyEmotionId)
    }

    @Test
    fun `감정 정보 수정`() {
        // given
        val dummyEmotionId: ID = dummyEmotion.id
        val request: UpdateEmotionRequestDto = DummyEmotion.toUpdateRequestDto()
        `when`(
            service.update(
                emotionId = dummyEmotionId,
                request = request
            )
        ).thenReturn(dummyEmotionId)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.update(
            emotionId = dummyEmotionId,
            request = request
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(dummyEmotionId)
    }

    @Test
    fun `감정 정보 삭제`() {
        // given
        val dummyEmotionId: ID = dummyEmotion.id
        `when`(service.delete(emotionId = dummyEmotionId)).thenReturn(true)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.delete(emotionId = dummyEmotionId)

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(true)
    }
}
