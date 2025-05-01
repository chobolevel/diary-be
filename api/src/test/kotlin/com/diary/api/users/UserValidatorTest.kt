package com.diary.api.users

import com.diary.api.dto.users.ChangeUserPasswordRequestDto
import com.diary.api.dto.users.CreateUserRequestDto
import com.diary.api.dto.users.UpdateUserRequestDto
import com.diary.api.service.users.validator.UserValidator
import com.diary.domain.entity.users.UserScopeType
import com.diary.domain.entity.users.UserSignUpType
import com.diary.domain.entity.users.UserUpdateMask
import com.diary.domain.exception.ErrorCode
import com.diary.domain.exception.InvalidParameterException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpStatus

@DisplayName("회원 validation 단위 테스트")
class UserValidatorTest {

    private val validator: UserValidator = UserValidator()

    @Test
    fun `회원 가입 시 usernanme 영어 또는 숫자로만 구성되지 않은 케이스`() {
        // given
        val request: CreateUserRequestDto = CreateUserRequestDto(
            username = "rodaka123@",
            password = "rkddlswo218@",
            signUpType = UserSignUpType.GENERAL,
            nickname = "알감자",
            scope = UserScopeType.PUBLIC
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request = request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[username]은(는) 반드시 영어 또는 숫자만 가능합니다.(최소 6자)")
    }

    @Test
    fun `회원 가입 시 username 6자 미만인 케이스`() {
        // given
        val request: CreateUserRequestDto = CreateUserRequestDto(
            username = "rodak",
            password = "rkddlswo218@",
            signUpType = UserSignUpType.GENERAL,
            nickname = "알감자",
            scope = UserScopeType.PUBLIC
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request = request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[username]은(는) 반드시 영어 또는 숫자만 가능합니다.(최소 6자)")
    }

    @Test
    fun `회원 가입 시 password 영어, 숫자, 특수문자를 포함하지 않는 케이스`() {
        // given
        val request: CreateUserRequestDto = CreateUserRequestDto(
            username = "rodaka123",
            password = "rkddlswo218",
            signUpType = UserSignUpType.GENERAL,
            nickname = "알감자",
            scope = UserScopeType.PUBLIC
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request = request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[password]은(는) 반드시 영어, 숫자, 특수문자(!@#$)로 구성되고 최소 8자 이상이어야 합니다.")
    }

    @Test
    fun `회원 가입 시 password 8자 미만인 케이스`() {
        // given
        val request: CreateUserRequestDto = CreateUserRequestDto(
            username = "rodaka123",
            password = "rkd218@",
            signUpType = UserSignUpType.GENERAL,
            nickname = "알감자",
            scope = UserScopeType.PUBLIC
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request = request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[password]은(는) 반드시 영어, 숫자, 특수문자(!@#$)로 구성되고 최소 8자 이상이어야 합니다.")
    }

    @Test
    fun `회원 가입 시 nickname 영어 또는 한글 또는 숫자로 구성되지 않은 케이스`() {
        // given
        val request: CreateUserRequestDto = CreateUserRequestDto(
            username = "rodaka123",
            password = "rkddlswo218@",
            signUpType = UserSignUpType.GENERAL,
            nickname = "알감자abc123@",
            scope = UserScopeType.PUBLIC
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request = request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[nickname]은(는) 반드시 영어 또는 한글 또는 숫자만 가능합니다.")
    }

    @Test
    fun `회원 nickname 수정 시 NULL 케이스`() {
        // given
        val request: UpdateUserRequestDto = UpdateUserRequestDto(
            nickname = null,
            scope = null,
            updateMask = listOf(UserUpdateMask.NICKNAME)
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request = request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[nickname]은(는) 필수 값입니다.")
    }

    @Test
    fun `회원 nickname 수정 시 영어 또는 한글 또는 숫자로 구성되지 않은 케이스`() {
        // given
        val request: UpdateUserRequestDto = UpdateUserRequestDto(
            nickname = "알감자abc123@",
            scope = null,
            updateMask = listOf(UserUpdateMask.NICKNAME)
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request = request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[nickname]은(는) 반드시 영어 또는 한글 또는 숫자만 가능합니다.")
    }

    @Test
    fun `회원 scope 수정 시 NULL 케이스`() {
        // given
        val request: UpdateUserRequestDto = UpdateUserRequestDto(
            nickname = null,
            scope = null,
            updateMask = listOf(UserUpdateMask.SCOPE)
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request = request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[scope]은(는) 필수 값입니다.")
    }

    @Test
    fun `회원 비밀번호 변경 시 newPassword 영어, 숫자, 특수문자를 포함하지 않는 케이스`() {
        // given
        val request: ChangeUserPasswordRequestDto = ChangeUserPasswordRequestDto(
            curPassword = "rkddlswo218@",
            newPassword = "rkddlswo218",
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request = request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[new_password]은(는) 반드시 영어, 숫자, 특수문자(!@#$)로 구성되고 최소 8자 이상이어야 합니다.")
    }

    @Test
    fun `회원 비밀번호 변경 시 newPassword 8자 미만 케이스`() {
        // given
        val request: ChangeUserPasswordRequestDto = ChangeUserPasswordRequestDto(
            curPassword = "rkddlswo218@",
            newPassword = "rkd218@"
        )

        // when
        val exception: InvalidParameterException = assertThrows {
            validator.validate(request = request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_PARAMETER)
        assertThat(exception.status).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(exception.message).isEqualTo("[new_password]은(는) 반드시 영어, 숫자, 특수문자(!@#$)로 구성되고 최소 8자 이상이어야 합니다.")
    }
}
