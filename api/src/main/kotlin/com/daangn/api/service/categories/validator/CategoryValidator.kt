package com.daangn.api.service.categories.validator

import com.daangn.api.dto.categories.CreateCategoryRequestDto
import com.daangn.api.dto.categories.UpdateCategoryRequestDto
import com.daangn.api.util.Regexps
import com.daangn.domain.entity.categories.CategoryUpdateMask
import com.daangn.domain.exception.ErrorCode
import com.daangn.domain.exception.InvalidParameterException
import org.springframework.stereotype.Component

@Component
class CategoryValidator {

    fun validate(request: CreateCategoryRequestDto) {
        validateName(
            input = request.name,
            parameterName = "name"
        )
        validatePositiveNumber(
            input = request.order,
            parameterName = "order"
        )
    }

    fun validate(request: UpdateCategoryRequestDto) {
        request.updateMask.forEach {
            when (it) {
                CategoryUpdateMask.ICON_URL -> {
                    if (request.iconUrl.isNullOrEmpty()) {
                        throw InvalidParameterException(
                            errorCode = ErrorCode.INVALID_PARAMETER,
                            message = "[icon_url]은(는) 필수 값입니다."
                        )
                    }
                }
                CategoryUpdateMask.NAME -> {
                    validateName(
                        input = request.name,
                        parameterName = "name"
                    )
                }
                CategoryUpdateMask.ORDER -> {
                    validatePositiveNumber(
                        input = request.order,
                        parameterName = "order"
                    )
                }
            }
        }
    }

    private fun validateName(input: String?, parameterName: String) {
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

    private fun validatePositiveNumber(input: Int?, parameterName: String) {
        if (input == null) {
            throw InvalidParameterException(
                errorCode = ErrorCode.INVALID_PARAMETER,
                message = "[$parameterName]은(는) 필수 값입니다."
            )
        }
        if (input < 1) {
            throw InvalidParameterException(
                errorCode = ErrorCode.INVALID_PARAMETER,
                message = "[$parameterName]은(는) 반드시 0보다 커야 합니다."
            )
        }
    }
}
