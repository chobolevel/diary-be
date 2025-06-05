package com.diary.api.users.points

import com.diary.api.dto.users.points.AddUserPointRequestDto
import com.diary.api.dto.users.points.SubUserPointRequestDto
import com.diary.api.service.users.validator.UserPointValidator
import com.diary.domain.exception.ErrorCode
import com.diary.domain.exception.InvalidParameterException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpStatus

@DisplayName("회원 포인트 validation 단위 테스트")
class UserPointValidatorTest {

    private val validator: UserPointValidator = UserPointValidator()

    @Test
    fun `회원 포인트 지급 시 포인트 100 미만인 케이스`() {
        // given
        val request = AddUserPointRequestDto(
            amount = 0,
            reason = "회원 가입 환영 포인트"
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request = request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[amount]은(는) 반드시 100 이상이어야 합니다.")
    }

    @Test
    fun `회원 포인트 차감 시 포인트 100 미만인 케이스`() {
        // given
        val request = SubUserPointRequestDto(
            amount = 0,
            reason = "회원 가입 환영 포인트 회수"
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request = request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[amount]은(는) 반드시 100 이상이어야 합니다.")
    }
}
