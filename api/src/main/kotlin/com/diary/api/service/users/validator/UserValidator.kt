package com.diary.api.service.users.validator

import com.diary.api.dto.users.ChangeUserPasswordRequestDto
import com.diary.api.dto.users.CreateUserRequestDto
import com.diary.api.dto.users.UpdateUserRequestDto
import com.diary.api.util.Regexps.nicknameRegexp
import com.diary.api.util.Regexps.passwordRegexp
import com.diary.api.util.Regexps.usernameRegex
import com.diary.api.util.validateIsNull
import com.diary.domain.entity.users.UserUpdateMask
import com.diary.domain.exception.ErrorCode
import com.diary.domain.exception.InvalidParameterException
import org.springframework.stereotype.Component

@Component
class UserValidator {

    fun validate(request: CreateUserRequestDto) {
        if (!usernameRegex.matches(request.username)) {
            throw InvalidParameterException(
                errorCode = ErrorCode.INVALID_PARAMETER,
                message = "[username]은(는) 반드시 영어 또는 숫자만 가능합니다.(최소 6자)"
            )
        }
        if (!passwordRegexp.matches(request.password)) {
            throw InvalidParameterException(
                errorCode = ErrorCode.INVALID_PARAMETER,
                message = "[password]은(는) 반드시 영어, 숫자, 특수문자(!@#$)로 구성되고 최소 8자 이상이어야 합니다."
            )
        }
        if (!nicknameRegexp.matches(request.nickname)) {
            throw InvalidParameterException(
                errorCode = ErrorCode.INVALID_PARAMETER,
                message = "[nickname]은(는) 반드시 영어 또는 한글 또는 숫자만 가능합니다."
            )
        }
    }

    fun validate(request: UpdateUserRequestDto) {
        request.updateMask.forEach {
            when (it) {
                UserUpdateMask.NICKNAME -> {
                    request.nickname.validateIsNull(parameterName = "nickname")
                    if (!nicknameRegexp.matches(request.nickname!!)) {
                        throw InvalidParameterException(
                            errorCode = ErrorCode.INVALID_PARAMETER,
                            message = "[nickname]은(는) 반드시 영어 또는 한글 또는 숫자만 가능합니다."
                        )
                    }
                }
                UserUpdateMask.SCOPE -> {
                    request.scope.validateIsNull(parameterName = "scope")
                }
            }
        }
    }

    fun validate(request: ChangeUserPasswordRequestDto) {
        if (!passwordRegexp.matches(request.newPassword)) {
            throw InvalidParameterException(
                errorCode = ErrorCode.INVALID_PARAMETER,
                message = "[new_password]은(는) 반드시 영어, 숫자, 특수문자(!@#$)로 구성되고 최소 8자 이상이어야 합니다."
            )
        }
    }
}
