package com.scrimmers.api.service.auth.validator

import com.scrimmers.api.dto.auth.LoginRequestDto
import com.scrimmers.domain.entity.user.UserLoginType
import com.scrimmers.domain.exception.ErrorCode
import com.scrimmers.domain.exception.ParameterInvalidException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class AuthValidator {

    fun validate(request: LoginRequestDto) {
        when (request.loginType) {
            UserLoginType.GENERAL -> {
                if (request.password.isNullOrEmpty()) {
                    throw ParameterInvalidException(
                        errorCode = ErrorCode.PARAMETER_INVALID,
                        status = HttpStatus.BAD_REQUEST,
                        message = "비밀번호는 필수 값입니다."
                    )
                }
            }

            else -> {
                if (request.socialId.isNullOrEmpty()) {
                    throw ParameterInvalidException(
                        errorCode = ErrorCode.PARAMETER_INVALID,
                        status = HttpStatus.BAD_REQUEST,
                        message = "소셜 로그인 시 소셜 아이디는 필수 값입니다."
                    )
                }
            }
        }
    }
}
