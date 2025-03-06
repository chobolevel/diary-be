package com.daangn.api.auth

import com.daangn.api.dto.auth.LoginRequestDto
import com.daangn.api.service.auth.validator.AuthValidator
import com.daangn.domain.entity.users.UserSignUpType
import com.daangn.domain.exception.ErrorCode
import com.daangn.domain.exception.InvalidParameterException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpStatus

@DisplayName("인증 검증기 단위 테스트")
class AuthValidatorTest {

    private lateinit var validator: AuthValidator

    @BeforeEach
    fun setup() {
        validator = AuthValidator()
    }

    @Test
    fun `로그인 시 이메일 형식 올바르지 않은 케이스`() {
        // given
        val request = LoginRequestDto(
            email = "rodaka123@naver",
            password = "rkddlswo218@",
            socialId = null,
            signUpType = UserSignUpType.GENERAL
        )

        // when
        val exception: InvalidParameterException = assertThrows<InvalidParameterException> {
            validator.validate(request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[email]의 이메일 형식이 올바르지 않습니다.")
    }

    @Test
    fun `일반 로그인 시 비밀번호 NULL 케이스`() {
        // given
        val request = LoginRequestDto(
            email = "rodaka123@naver.com",
            password = null,
            socialId = null,
            signUpType = UserSignUpType.GENERAL
        )

        // when
        val exception: InvalidParameterException = assertThrows<InvalidParameterException> {
            validator.validate(request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[password]은(는) 필수 값입니다.")
    }

    @Test
    fun `소셜 로그인 시 소셜 아이디 NULL 케이스`() {
        // given
        val request = LoginRequestDto(
            email = "rodaka123@naver.com",
            password = null,
            socialId = null,
            signUpType = UserSignUpType.KAKAO
        )

        // when
        val exception: InvalidParameterException = assertThrows<InvalidParameterException> {
            validator.validate(request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[social_id]은(는) 필수 값입니다.")
    }
}
