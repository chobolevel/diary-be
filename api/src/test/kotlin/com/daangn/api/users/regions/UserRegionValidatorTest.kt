package com.daangn.api.users.regions

import com.daangn.api.dto.users.regions.CreateUserRegionRequestDto
import com.daangn.api.dto.users.regions.UpdateUserRegionRequestDto
import com.daangn.api.service.users.validator.UserRegionValidator
import com.daangn.domain.entity.users.regions.UserRegionUpdateMask
import com.daangn.domain.exception.ErrorCode
import com.daangn.domain.exception.InvalidParameterException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpStatus

@DisplayName("회원 지역 검증기 단위 테스트")
class UserRegionValidatorTest {

    private lateinit var validator: UserRegionValidator

    @BeforeEach
    fun setup() {
        validator = UserRegionValidator()
    }

    @Test
    fun `회원 지역 등록 시 시,도명 올바르지 않은 케이스`() {
        // given
        val request = CreateUserRegionRequestDto(
            latitude = 35.2602308946252,
            longitude = 128.636698019516,
            firstDepthName = "경남@",
            secondDepthName = "창원시 의창구",
            thirdDepthName = "도계동"
        )

        // when
        val exception: InvalidParameterException = assertThrows<InvalidParameterException> {
            validator.validate(request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[first_depth_name]은(는) 영어 또는 한글 또는 숫자만 입력 가능합니다.")
    }

    @Test
    fun `회원 지역 등록 시 구명 올바르지 않은 케이스`() {
        // given
        val request = CreateUserRegionRequestDto(
            latitude = 35.2602308946252,
            longitude = 128.636698019516,
            firstDepthName = "경남",
            secondDepthName = "창원시 의창구@",
            thirdDepthName = "도계동"
        )

        // when
        val exception: InvalidParameterException = assertThrows<InvalidParameterException> {
            validator.validate(request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[second_depth_name]은(는) 영어 또는 한글 또는 숫자만 입력 가능합니다.")
    }

    @Test
    fun `회원 지역 등록 시 읍,면,동명 올바르지 않은 케이스`() {
        // given
        val request = CreateUserRegionRequestDto(
            latitude = 35.2602308946252,
            longitude = 128.636698019516,
            firstDepthName = "경남",
            secondDepthName = "창원시 의창구",
            thirdDepthName = "도계동@"
        )

        // when
        val exception: InvalidParameterException = assertThrows<InvalidParameterException> {
            validator.validate(request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[third_depth_name]은(는) 영어 또는 한글 또는 숫자만 입력 가능합니다.")
    }

    @Test
    fun `회원 지역 위도 수정 시 NULL 케이스`() {
        // given
        val request = UpdateUserRegionRequestDto(
            latitude = null,
            longitude = null,
            firstDepthName = null,
            secondDepthName = null,
            thirdDepthName = null,
            updateMask = listOf(
                UserRegionUpdateMask.LATITUDE
            )
        )

        // when
        val exception: InvalidParameterException = assertThrows<InvalidParameterException> {
            validator.validate(request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[latitude]은(는) 필수 값입니다.")
    }

    @Test
    fun `회원 지역 경도 수정 시 NULL 케이스`() {
        // given
        val request = UpdateUserRegionRequestDto(
            latitude = null,
            longitude = null,
            firstDepthName = null,
            secondDepthName = null,
            thirdDepthName = null,
            updateMask = listOf(
                UserRegionUpdateMask.LONGITUDE
            )
        )

        // when
        val exception: InvalidParameterException = assertThrows<InvalidParameterException> {
            validator.validate(request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[longitude]은(는) 필수 값입니다.")
    }

    @Test
    fun `회원 지역 시,도명 수정 시 NULL 케이스`() {
        // given
        val request = UpdateUserRegionRequestDto(
            latitude = null,
            longitude = null,
            firstDepthName = null,
            secondDepthName = null,
            thirdDepthName = null,
            updateMask = listOf(
                UserRegionUpdateMask.FIRST_DEPTH_NAME
            )
        )

        // when
        val exception: InvalidParameterException = assertThrows<InvalidParameterException> {
            validator.validate(request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[first_depth_name]은(는) 필수 값입니다.")
    }

    @Test
    fun `회원 지역 시, 도명 수정 시 올바르지 않은 케이스`() {
        // given
        val request = UpdateUserRegionRequestDto(
            latitude = null,
            longitude = null,
            firstDepthName = "경남!",
            secondDepthName = null,
            thirdDepthName = null,
            updateMask = listOf(
                UserRegionUpdateMask.FIRST_DEPTH_NAME
            )
        )

        // when
        val exception: InvalidParameterException = assertThrows<InvalidParameterException> {
            validator.validate(request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[first_depth_name]은(는) 영어 또는 한글 또는 숫자만 입력 가능합니다.")
    }

    @Test
    fun `회원 지역 구명 수정 시 NULL 케이스`() {
        // given
        val request = UpdateUserRegionRequestDto(
            latitude = null,
            longitude = null,
            firstDepthName = null,
            secondDepthName = null,
            thirdDepthName = null,
            updateMask = listOf(
                UserRegionUpdateMask.SECOND_DEPTH_NAME
            )
        )

        // when
        val exception: InvalidParameterException = assertThrows<InvalidParameterException> {
            validator.validate(request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[second_depth_name]은(는) 필수 값입니다.")
    }

    @Test
    fun `회원 지역 구명 수정 시 올바르지 않은 케이스`() {
        // given
        val request = UpdateUserRegionRequestDto(
            latitude = null,
            longitude = null,
            firstDepthName = null,
            secondDepthName = "창원시 의창구@",
            thirdDepthName = null,
            updateMask = listOf(
                UserRegionUpdateMask.SECOND_DEPTH_NAME
            )
        )

        // when
        val exception: InvalidParameterException = assertThrows<InvalidParameterException> {
            validator.validate(request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[second_depth_name]은(는) 영어 또는 한글 또는 숫자만 입력 가능합니다.")
    }

    @Test
    fun `회원 지역 읍,명,동명 수정 시 NULL 케이스`() {
        // given
        val request = UpdateUserRegionRequestDto(
            latitude = null,
            longitude = null,
            firstDepthName = null,
            secondDepthName = null,
            thirdDepthName = null,
            updateMask = listOf(
                UserRegionUpdateMask.THIRD_DEPTH_NAME
            )
        )

        // when
        val exception: InvalidParameterException = assertThrows<InvalidParameterException> {
            validator.validate(request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[third_depth_name]은(는) 필수 값입니다.")
    }

    @Test
    fun `회원 지역 읍,면,동명 수정 시 올바르지 않은 케이스`() {
        // given
        val request = UpdateUserRegionRequestDto(
            latitude = null,
            longitude = null,
            firstDepthName = null,
            secondDepthName = null,
            thirdDepthName = "도계동@",
            updateMask = listOf(
                UserRegionUpdateMask.THIRD_DEPTH_NAME
            )
        )

        // when
        val exception: InvalidParameterException = assertThrows<InvalidParameterException> {
            validator.validate(request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[third_depth_name]은(는) 영어 또는 한글 또는 숫자만 입력 가능합니다.")
    }
}
