package com.daangn.api.service.auth.validator

import com.daangn.api.dto.auth.LoginRequestDto
import com.daangn.api.util.Regexps
import com.daangn.domain.entity.users.UserSignUpType
import com.daangn.domain.exception.ErrorCode
import com.daangn.domain.exception.InvalidParameterException
import org.springframework.stereotype.Component

@Component
class AuthValidator {

    fun validate(request: LoginRequestDto) {
        validateEmail(
            input = request.email,
            parameterName = "email"
        )
        when (request.signUpType) {
            UserSignUpType.GENERAL -> {
                validateNull(
                    input = request.password,
                    parameterName = "password"
                )
            }
            else -> {
                validateNull(
                    input = request.socialId,
                    parameterName = "social_id"
                )
            }
        }
    }

    private fun validateEmail(
        input: String,
        parameterName: String
    ) {
        if (!Regexps.emailRegexp.matches(input)) {
            throw InvalidParameterException(
                errorCode = ErrorCode.INVALID_PARAMETER,
                message = "[$parameterName]의 이메일 형식이 올바르지 않습니다."
            )
        }
    }
    private fun validateNull(
        input: String?,
        parameterName: String
    ) {
        if (input.isNullOrEmpty()) {
            throw InvalidParameterException(
                errorCode = ErrorCode.INVALID_PARAMETER,
                message = "[$parameterName]은(는) 필수 값입니다."
            )
        }
    }
}
