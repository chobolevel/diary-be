package com.scrimmers.api.service.user.validator

import com.scrimmers.api.dto.user.CreateUserRequestDto
import com.scrimmers.api.dto.user.UpdateUserRequestDto
import com.scrimmers.domain.entity.user.UserFinder
import com.scrimmers.domain.entity.user.UserLoginType
import com.scrimmers.domain.entity.user.UserUpdateMask
import com.scrimmers.domain.exception.ErrorCode
import com.scrimmers.domain.exception.ParameterInvalidException
import com.scrimmers.domain.exception.PolicyException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class UserValidator(
    private val userFinder: UserFinder
) {

    private final val emailRegexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"
    private final val nicknameRegexp = "^[가-힣]{2,20}$"
    private final val phoneRegexp = "^\\d{10,11}\$"

    fun validate(request: CreateUserRequestDto) {
        when (request.loginType) {
            // general
            UserLoginType.GENERAL -> {
                if (request.password.isNullOrEmpty()) {
                    throw ParameterInvalidException(
                        errorCode = ErrorCode.PARAMETER_INVALID,
                        status = HttpStatus.BAD_REQUEST,
                        message = "비밀번호는 필수 값입니다."
                    )
                }
            }
            // social
            else -> {
                if (request.socialId.isNullOrEmpty()) {
                    throw ParameterInvalidException(
                        errorCode = ErrorCode.PARAMETER_INVALID,
                        status = HttpStatus.BAD_REQUEST,
                        message = "소셜 아이디는 필수 값입니다."
                    )
                }
            }
        }
        validateEmail(request.email)
        validateNickname(request.nickname)
        validatePhone(request.phone)
    }

    fun validate(request: UpdateUserRequestDto) {
        request.updateMask.forEach {
            when (it) {
                UserUpdateMask.NICKNAME -> {
                    if (request.nickname.isNullOrEmpty()) {
                        throw ParameterInvalidException(
                            errorCode = ErrorCode.PARAMETER_INVALID,
                            status = HttpStatus.BAD_REQUEST,
                            message = "변경할 닉네임이 유효하지 않습니다."
                        )
                    }
                    validateNickname(request.nickname)
                }

                UserUpdateMask.PHONE -> {
                    if (request.phone.isNullOrEmpty()) {
                        throw ParameterInvalidException(
                            errorCode = ErrorCode.PARAMETER_INVALID,
                            status = HttpStatus.BAD_REQUEST,
                            message = "변경할 전화번호가 유효하지 않습니다."
                        )
                    }
                    validatePhone(request.phone)
                }

                UserUpdateMask.BIRTH -> {
                    if (request.birth == null) {
                        throw ParameterInvalidException(
                            errorCode = ErrorCode.PARAMETER_INVALID,
                            status = HttpStatus.BAD_REQUEST,
                            message = "변경할 생년월일이 유효하지 않습니다."
                        )
                    }
                }

                UserUpdateMask.GENDER -> {
                    if (request.gender == null) {
                        throw ParameterInvalidException(
                            errorCode = ErrorCode.PARAMETER_INVALID,
                            status = HttpStatus.BAD_REQUEST,
                            message = "변경할 성별이 유효하지 않습니다."
                        )
                    }
                }

                UserUpdateMask.MAIN_POSITION -> {
                    if (request.mainPosition == null) {
                        throw ParameterInvalidException(
                            errorCode = ErrorCode.PARAMETER_INVALID,
                            status = HttpStatus.BAD_REQUEST,
                            message = "변경할 주포지션이 유효하지 않습니다."
                        )
                    }
                }

                UserUpdateMask.SUB_POSITION -> {
                    if (request.subPosition == null) {
                        throw ParameterInvalidException(
                            errorCode = ErrorCode.PARAMETER_INVALID,
                            status = HttpStatus.BAD_REQUEST,
                            message = "변경할 보조포지션이 유효하지 않습니다."
                        )
                    }
                }
            }
        }
    }

    private fun validateEmail(email: String) {
        if (!email.matches(emailRegexp.toRegex())) {
            throw PolicyException(
                errorCode = ErrorCode.USER_EMAIL_IS_INCORRECT_FORMAT,
                status = HttpStatus.BAD_REQUEST,
                message = ErrorCode.USER_EMAIL_IS_INCORRECT_FORMAT.desc
            )
        }
        if (userFinder.existsByEmail(email)) {
            throw PolicyException(
                errorCode = ErrorCode.USER_EMAIL_IS_ALREADY_EXISTS,
                status = HttpStatus.BAD_REQUEST,
                message = ErrorCode.USER_EMAIL_IS_ALREADY_EXISTS.desc
            )
        }
    }

    private fun validateNickname(nickname: String) {
        if (!nickname.matches(nicknameRegexp.toRegex())) {
            throw PolicyException(
                errorCode = ErrorCode.USER_NICKNAME_IS_INCORRECT_FORMAT,
                status = HttpStatus.BAD_REQUEST,
                message = ErrorCode.USER_NICKNAME_IS_INCORRECT_FORMAT.desc
            )
        }
        if (userFinder.existsByNickname(nickname)) {
            throw PolicyException(
                errorCode = ErrorCode.USER_NICKNAME_IS_ALREADY_EXISTS,
                status = HttpStatus.BAD_REQUEST,
                message = ErrorCode.USER_NICKNAME_IS_ALREADY_EXISTS.desc
            )
        }
    }

    private fun validatePhone(phone: String) {
        if (!phone.matches(phoneRegexp.toRegex())) {
            throw PolicyException(
                errorCode = ErrorCode.USER_PHONE_IS_INCORRECT_FORMAT,
                status = HttpStatus.BAD_REQUEST,
                message = ErrorCode.USER_PHONE_IS_INCORRECT_FORMAT.desc
            )
        }
        if (userFinder.existsByPhone(phone)) {
            throw PolicyException(
                errorCode = ErrorCode.USER_PHONE_IS_ALREADY_EXISTS,
                status = HttpStatus.BAD_REQUEST,
                message = ErrorCode.USER_PHONE_IS_ALREADY_EXISTS.desc
            )
        }
    }
}
