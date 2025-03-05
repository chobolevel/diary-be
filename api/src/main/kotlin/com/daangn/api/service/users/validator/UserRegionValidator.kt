package com.daangn.api.service.users.validator

import com.daangn.api.dto.users.regions.CreateUserRegionRequestDto
import com.daangn.api.dto.users.regions.UpdateUserRegionRequestDto
import com.daangn.api.util.Regexps
import com.daangn.domain.entity.users.regions.UserRegionUpdateMask
import com.daangn.domain.exception.ErrorCode
import com.daangn.domain.exception.InvalidParameterException
import org.springframework.stereotype.Component

@Component
class UserRegionValidator {

    fun validate(request: CreateUserRegionRequestDto) {
        validateDepthName(
            input = request.firstDepthName,
            parameterName = "first_depth_name"
        )
        validateDepthName(
            input = request.secondDepthName,
            parameterName = "second_depth_name"
        )
        validateDepthName(
            input = request.thirdDepthName,
            parameterName = "third_depth_name"
        )
    }

    fun validate(request: UpdateUserRegionRequestDto) {
        request.updateMask.forEach {
            when (it) {
                UserRegionUpdateMask.LATITUDE -> {
                    if (request.latitude == null) {
                        throw InvalidParameterException(
                            errorCode = ErrorCode.INVALID_PARAMETER,
                            message = "[latitude]은(는) 필수 값입니다."
                        )
                    }
                }
                UserRegionUpdateMask.LONGITUDE -> {
                    if (request.longitude == null) {
                        throw InvalidParameterException(
                            errorCode = ErrorCode.INVALID_PARAMETER,
                            message = "[longitude]은(는) 필수 값입니다."
                        )
                    }
                }
                UserRegionUpdateMask.FIRST_DEPTH_NAME -> {
                    validateDepthName(
                        input = request.firstDepthName,
                        parameterName = "first_depth_name"
                    )
                }
                UserRegionUpdateMask.SECOND_DEPTH_NAME -> {
                    validateDepthName(
                        input = request.secondDepthName,
                        parameterName = "second_depth_name"
                    )
                }
                UserRegionUpdateMask.THIRD_DEPTH_NAME -> {
                    validateDepthName(
                        input = request.thirdDepthName,
                        parameterName = "third_depth_name"
                    )
                }
            }
        }
    }

    private fun validateDepthName(
        input: String?,
        parameterName: String
    ) {
        if (input.isNullOrEmpty()) {
            throw InvalidParameterException(
                errorCode = ErrorCode.INVALID_PARAMETER,
                message = "[$parameterName]은(는) 필수 값입니다."
            )
        }
        if (!Regexps.nameRegexp.matches(input)) {
            throw InvalidParameterException(
                errorCode = ErrorCode.INVALID_PARAMETER,
                message = "[$parameterName]은(는) 영어 또는 한글 또는 숫자만 입력 가능합니다."
            )
        }
    }
}
