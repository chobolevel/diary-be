package com.diary.api.weathers

import com.diary.api.dto.weathers.CreateWeatherRequestDto
import com.diary.api.dto.weathers.UpdateWeatherRequestDto
import com.diary.api.service.weathers.validator.WeatherValidator
import com.diary.domain.entity.weathers.WeatherUpdateMask
import com.diary.domain.exception.ErrorCode
import com.diary.domain.exception.InvalidParameterException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpStatus

@DisplayName("날씨 validation 단위 테스트")
class WeatherValidatorTest {

    private val validator: WeatherValidator = WeatherValidator()

    @Test
    fun `날씨 등록 시 정렬 순서 0 미만인 케이스`() {
        // given
        val request = CreateWeatherRequestDto(
            name = "맑음",
            icon = "☀\uFE0F",
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
    fun `날씨 네이밍 수정 시 NULL 케이스`() {
        // given
        val request = UpdateWeatherRequestDto(
            name = null,
            icon = null,
            order = null,
            updateMask = listOf(WeatherUpdateMask.NAME)
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
    fun `날씨 아이콘 수정 시 NULL 케이스`() {
        // given
        val request = UpdateWeatherRequestDto(
            name = null,
            icon = null,
            order = null,
            updateMask = listOf(WeatherUpdateMask.ICON)
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
    fun `날씨 정렬 순서 수정 시 NULL 케이스`() {
        // given
        val request = UpdateWeatherRequestDto(
            name = null,
            icon = null,
            order = null,
            updateMask = listOf(WeatherUpdateMask.ORDER)
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

    @Test
    fun `날씨 정렬 순서 수정 시 0 미만인 케이스`() {
        // given
        val request = UpdateWeatherRequestDto(
            name = null,
            icon = null,
            order = -1,
            updateMask = listOf(WeatherUpdateMask.ORDER)
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
}
