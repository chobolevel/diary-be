package com.daangn.api.users

import com.daangn.api.dto.users.CreateUserRequestDto
import com.daangn.api.dto.users.UpdateUserRequestDto
import com.daangn.api.service.users.validator.UserValidator
import com.daangn.domain.entity.users.UserRepositoryWrapper
import com.daangn.domain.entity.users.UserSignUpType
import com.daangn.domain.entity.users.UserUpdateMask
import com.daangn.domain.exception.ErrorCode
import com.daangn.domain.exception.InvalidParameterException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus

@ExtendWith(MockitoExtension::class)
@DisplayName("회원 검증기 단위 테스트")
class UserValidatorTest {

    @Mock
    private lateinit var repositoryWrapper: UserRepositoryWrapper

    @InjectMocks
    private lateinit var validator: UserValidator

    @Test
    fun `회원 가입 시 이메일 형식 올바르지 않은 케이스`() {
        // given
        val request = CreateUserRequestDto(
            email = "rodaka123@naver",
            password = "rkddlswo218@",
            socialId = null,
            signUpType = UserSignUpType.GENERAL,
            nickname = "알감자"
        )

        // when
        val exception: InvalidParameterException = assertThrows<InvalidParameterException> {
            validator.validate(
                request = request
            )
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[email]의 이메일 형식이 올바르지 않습니다.")
    }

    @Test
    fun `일반 회원 가입 시 비밀번호 NULL 케이스`() {
        // given
        val request = CreateUserRequestDto(
            email = "rodaka123@naver.com",
            password = null,
            socialId = null,
            signUpType = UserSignUpType.GENERAL,
            nickname = "알감자"
        )

        // when
        val exception: InvalidParameterException = assertThrows<InvalidParameterException> {
            validator.validate(
                request = request
            )
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[password]은(는) 필수 값입니다.")
    }

    @Test
    fun `일반 회원 가입 시 비밀번호 올바르지 않은 케이스`() {
        // given
        val request = CreateUserRequestDto(
            email = "rodaka123@naver.com",
            password = "rkddlswo",
            socialId = null,
            signUpType = UserSignUpType.GENERAL,
            nickname = "알감자"
        )

        // when
        val exception: InvalidParameterException = assertThrows<InvalidParameterException> {
            validator.validate(
                request = request
            )
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[password]은(는) 영문 + 숫자 + 특수문자 조합으로 8자리 이상이어야 합니다.")
    }

    @Test
    fun `소셜 회원 가입 시 소셜 아이디 NULL 케이스`() {
        // given
        val request = CreateUserRequestDto(
            email = "rodaka123@naver.com",
            password = null,
            socialId = null,
            signUpType = UserSignUpType.KAKAO,
            nickname = "알감자"
        )

        // when
        val exception: InvalidParameterException = assertThrows<InvalidParameterException> {
            validator.validate(
                request = request
            )
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[social_id]은(는) 필수 값입니다.")
    }

    @Test
    fun `회원 가입 시 닉네임 올바르지 않은 케이스`() {
        // given
        val request = CreateUserRequestDto(
            email = "rodaka123@naver.com",
            password = "rkddlswo218@",
            socialId = null,
            signUpType = UserSignUpType.GENERAL,
            nickname = "강인재@"
        )

        // when
        val exception: InvalidParameterException = assertThrows<InvalidParameterException> {
            validator.validate(
                request = request
            )
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[nickname]은(는) 영어 또는 한글 또는 숫자만 사용 가능합니다.")
    }

    @Test
    fun `회원 닉네임 수정 시 닉네임 NULL 케이스`() {
        // given
        val request = UpdateUserRequestDto(
            nickname = null,
            updateMask = listOf(
                UserUpdateMask.NICKNAME
            )
        )

        // when
        val exception: InvalidParameterException = assertThrows<InvalidParameterException> {
            validator.validate(
                request = request
            )
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[nickname]은(는) 필수 값입니다.")
    }

    @Test
    fun `회원 닉네임 수정 시 닉네임 올바르지 않은 케이스`() {
        // given
        val request = UpdateUserRequestDto(
            nickname = "알감자@",
            updateMask = listOf(
                UserUpdateMask.NICKNAME
            )
        )

        // when
        val exception: InvalidParameterException = assertThrows<InvalidParameterException> {
            validator.validate(
                request = request
            )
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[nickname]은(는) 영어 또는 한글 또는 숫자만 사용 가능합니다.")
    }
}
