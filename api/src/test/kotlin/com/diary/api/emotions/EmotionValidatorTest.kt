package com.diary.api.emotions

import com.diary.api.dto.emotions.CreateEmotionRequestDto
import com.diary.api.dto.emotions.UpdateEmotionRequestDto
import com.diary.api.service.emotions.validator.EmotionValidator
import com.diary.domain.entity.emotions.EmotionUpdateMask
import com.diary.domain.exception.ErrorCode
import com.diary.domain.exception.InvalidParameterException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpStatus

@DisplayName("감정 validation 단위 테스트")
class EmotionValidatorTest {

    private val validator: EmotionValidator = EmotionValidator()

    @Test
    fun `감정 등록 시 정렬 순서 0 미만인 케이스`() {
        // given
        val request = CreateEmotionRequestDto(
            name = "기분 좋음",
            icon = "\uD83D\uDE0A",
            order = -1
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request = request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[order]은(는) 반드시 0 이상이어야 합니다.")
    }

    @Test
    fun `감정 네이밍 수정 시 NULL 케이스`() {
        // given
        val request = UpdateEmotionRequestDto(
            name = null,
            icon = null,
            order = null,
            updateMask = listOf(EmotionUpdateMask.NAME)
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request = request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[name]은(는) 필수 값입니다.")
    }

    @Test
    fun `감정 아이콘 수정 시 NULL 케이스`() {
        // given
        val request = UpdateEmotionRequestDto(
            name = null,
            icon = null,
            order = null,
            updateMask = listOf(EmotionUpdateMask.ICON)
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request = request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[icon]은(는) 필수 값입니다.")
    }

    @Test
    fun `감정 정렬 순서 수정 시 NULL 케이스`() {
        // given
        val request = UpdateEmotionRequestDto(
            name = null,
            icon = null,
            order = null,
            updateMask = listOf(EmotionUpdateMask.ORDER)
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request = request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[order]은(는) 필수 값입니다.")
    }
}
