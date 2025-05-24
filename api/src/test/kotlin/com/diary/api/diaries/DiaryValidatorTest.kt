package com.diary.api.diaries

import com.diary.api.dto.diaries.CreateDiaryRequestDto
import com.diary.api.dto.diaries.UpdateDiaryRequestDto
import com.diary.api.service.diaries.validator.DiaryValidator
import com.diary.domain.entity.diaries.DiaryUpdateMask
import com.diary.domain.exception.ErrorCode
import com.diary.domain.exception.InvalidParameterException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpStatus

@DisplayName("일기 validation 로직 단위 테스트")
class DiaryValidatorTest {

    private val validator: DiaryValidator = DiaryValidator()

    @Test
    fun `일기 등록 시 제목 10자 미만인 케이스`() {
        // given
        val request = CreateDiaryRequestDto(
            weatherId = "0KH4WDSJA2CHB",
            emotionId = "0KH4WDSJA2CHB",
            title = "제목",
            content = "일기 내용을 입력해주세요. 오늘 하루는 어떤 하루였나요?",
            isSecret = false
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request = request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[title]은(는) 공백 포함 10자 이상이어야 합니다.")
    }

    @Test
    fun `일기 등록 시 내용 20자 미만인 케이스`() {
        // given
        val request = CreateDiaryRequestDto(
            weatherId = "0KH4WDSJA2CHB",
            emotionId = "0KH4WDSJA2CHB",
            title = "일기 제목을 입력해주세요.",
            content = "일기 내용",
            isSecret = false
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request = request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[content]은(는) 공백 포함 20자 이상이어야 합니다.")
    }

    @Test
    fun `일기 날씨 수정 시 날씨 아이디 NULL 케이스`() {
        // given
        val request = UpdateDiaryRequestDto(
            weatherId = null,
            emotionId = null,
            title = null,
            content = null,
            isSecret = null,
            updateMask = listOf(DiaryUpdateMask.WEATHER)
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request = request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[weather_id]은(는) 필수 값입니다.")
    }

    @Test
    fun `일기 감정 수정 시 감정 아이디 NULL 케이스`() {
        // given
        val request = UpdateDiaryRequestDto(
            weatherId = null,
            emotionId = null,
            title = null,
            content = null,
            isSecret = null,
            updateMask = listOf(DiaryUpdateMask.EMOTION)
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request = request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[emotion_id]은(는) 필수 값입니다.")
    }

    @Test
    fun `일기 제목 수정 시 NULL 케이스`() {
        // given
        val request = UpdateDiaryRequestDto(
            weatherId = null,
            emotionId = null,
            title = null,
            content = null,
            isSecret = null,
            updateMask = listOf(DiaryUpdateMask.TITLE)
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request = request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[title]은(는) 필수 값입니다.")
    }

    @Test
    fun `일기 제목 수정 시 제목 공백포함 10자 미만인 케이스`() {
        // given
        val request = UpdateDiaryRequestDto(
            weatherId = null,
            emotionId = null,
            title = "제목",
            content = null,
            isSecret = null,
            updateMask = listOf(DiaryUpdateMask.TITLE)
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request = request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[title]은(는) 공백 포함 10자 이상이어야 합니다.")
    }

    @Test
    fun `일기 내용 수정 시 NULL 케이스`() {
        // given
        val request = UpdateDiaryRequestDto(
            weatherId = null,
            emotionId = null,
            title = null,
            content = null,
            isSecret = null,
            updateMask = listOf(DiaryUpdateMask.CONTENT)
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request = request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[content]은(는) 필수 값입니다.")
    }

    @Test
    fun `일기 내용 수정 시 공백 포함 20자 미만인 케이스`() {
        // given
        val request = UpdateDiaryRequestDto(
            weatherId = null,
            emotionId = null,
            title = null,
            content = "내용",
            isSecret = null,
            updateMask = listOf(DiaryUpdateMask.CONTENT)
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request = request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[content]은(는) 공백 포함 20자 이상이어야 합니다.")
    }

    @Test
    fun `일기 비밀글 여부 수정 시 NULL 케이스`() {
        // given
        val request = UpdateDiaryRequestDto(
            weatherId = null,
            emotionId = null,
            title = null,
            content = null,
            isSecret = null,
            updateMask = listOf(DiaryUpdateMask.IS_SECRET)
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request = request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[is_secret]은(는) 필수 값입니다.")
    }
}
