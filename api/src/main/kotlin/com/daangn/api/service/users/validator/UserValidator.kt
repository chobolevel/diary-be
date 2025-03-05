package com.daangn.api.service.users.validator

import com.daangn.api.dto.users.CreateUserRequestDto
import com.daangn.api.dto.users.UpdateUserRequestDto
import com.daangn.api.util.Regexps
import com.daangn.domain.entity.users.UserRepositoryWrapper
import com.daangn.domain.entity.users.UserSignUpType
import com.daangn.domain.entity.users.UserUpdateMask
import com.daangn.domain.exception.ErrorCode
import com.daangn.domain.exception.InvalidParameterException
import com.daangn.domain.exception.PolicyException
import org.springframework.stereotype.Component

@Component
class UserValidator(
    private val repositoryWrapper: UserRepositoryWrapper
) {

    fun validate(request: CreateUserRequestDto) {
        validateEmail(
            input = request.email,
            parameterName = "email"
        )
        if (repositoryWrapper.existsByEmail(request.email)) {
            throw PolicyException(
                errorCode = ErrorCode.USER_EMAIL_IS_DUPLICATED,
                message = ErrorCode.USER_EMAIL_IS_DUPLICATED.message
            )
        }
        when (request.signUpType) {
            UserSignUpType.GENERAL -> {
                validatePassword(
                    input = request.password,
                    parameterName = "password"
                )
            }
            else -> {
                validateSocialId(
                    input = request.socialId,
                    parameterName = "social_id"
                )
            }
        }
        validateNickname(
            input = request.nickname,
            parameterName = "nickname"
        )
    }

    fun validate(request: UpdateUserRequestDto) {
        request.updateMask.forEach {
            when (it) {
                UserUpdateMask.NICKNAME -> {
                    validateNickname(
                        input = request.nickname,
                        parameterName = "nickname"
                    )
                }
            }
        }
    }

    private fun validateEmail(input: String?, parameterName: String) {
        if (input.isNullOrEmpty()) {
            throw InvalidParameterException(
                errorCode = ErrorCode.INVALID_PARAMETER,
                message = "[$parameterName]은(는) 필수 값입니다."
            )
        }
        if (!Regexps.emailRegexp.matches(input)) {
            throw InvalidParameterException(
                errorCode = ErrorCode.INVALID_PARAMETER,
                message = "[$parameterName]의 이메일 형식이 올바르지 않습니다."
            )
        }
    }

    private fun validatePassword(input: String?, parameterName: String) {
        if (input.isNullOrEmpty()) {
            throw InvalidParameterException(
                errorCode = ErrorCode.INVALID_PARAMETER,
                message = "[$parameterName]은(는) 필수 값입니다."
            )
        }
        if (!Regexps.passwordRegexp.matches(input)) {
            throw InvalidParameterException(
                errorCode = ErrorCode.INVALID_PARAMETER,
                message = "[$parameterName]은(는) 영문 + 숫자 + 특수문자 조합으로 8자리 이상이어야 합니다."
            )
        }
    }

    private fun validateSocialId(input: String?, parameterName: String) {
        if (input.isNullOrEmpty()) {
            throw InvalidParameterException(
                errorCode = ErrorCode.INVALID_PARAMETER,
                message = "[$parameterName]은(는) 필수 값입니다."
            )
        }
    }

    private fun validateNickname(input: String?, parameterName: String) {
        if (input.isNullOrEmpty()) {
            throw InvalidParameterException(
                errorCode = ErrorCode.INVALID_PARAMETER,
                message = "[$parameterName]은(는) 필수 값입니다."
            )
        }
        if (!Regexps.nameRegexp.matches(input)) {
            throw InvalidParameterException(
                errorCode = ErrorCode.INVALID_PARAMETER,
                message = "[$parameterName]은(는) 영어 또는 한글 또는 숫자만 사용 가능합니다."
            )
        }
    }
}
