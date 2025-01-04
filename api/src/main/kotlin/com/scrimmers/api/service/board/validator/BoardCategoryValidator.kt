package com.scrimmers.api.service.board.validator

import com.scrimmers.api.dto.board.category.CreateBoardCategoryRequestDto
import com.scrimmers.api.dto.board.category.UpdateBoardCategoryRequestDto
import com.scrimmers.domain.entity.borad.category.BoardCategoryUpdateMask
import com.scrimmers.domain.exception.ErrorCode
import com.scrimmers.domain.exception.ParameterInvalidException
import org.springframework.stereotype.Component
import kotlin.jvm.Throws

@Component
class BoardCategoryValidator {

    private final val codeRegexp = "^[A-Za-z]+\$"
    private final val nameRegexp = "^[A-Za-z가-힣]+\$"

    fun validate(request: CreateBoardCategoryRequestDto) {
        validateCode(request.code)
        validateName(request.name)
    }

    fun validate(request: UpdateBoardCategoryRequestDto) {
        request.updateMask.forEach {
            when (it) {
                BoardCategoryUpdateMask.CODE -> {
                    if (request.code.isNullOrEmpty()) {
                        throw ParameterInvalidException(
                            errorCode = ErrorCode.PARAMETER_INVALID,
                            message = "변경할 카테고리 코드가 유효하지 않습니다."
                        )
                    }
                    validateCode(request.code)
                }

                BoardCategoryUpdateMask.NAME -> {
                    if (request.name.isNullOrEmpty()) {
                        throw ParameterInvalidException(
                            errorCode = ErrorCode.PARAMETER_INVALID,
                            message = "변경할 카테고리명이 유효하지 않습니다."
                        )
                    }
                    validateName(request.name)
                }

                BoardCategoryUpdateMask.ORDER -> {
                    if (request.order == null || request.order < 0) {
                        throw ParameterInvalidException(
                            errorCode = ErrorCode.PARAMETER_INVALID,
                            message = "변경할 카테고리 순서가 유효하지 않습니다.(음수 지정 불가)"
                        )
                    }
                }
            }
        }
    }

    @Throws(ParameterInvalidException::class)
    private fun validateCode(code: String) {
        if (!code.matches(codeRegexp.toRegex())) {
            throw ParameterInvalidException(
                errorCode = ErrorCode.PARAMETER_INVALID,
                message = "카테고리 코드는 영어 대소문자만 가능합니다."
            )
        }
    }

    @Throws(ParameterInvalidException::class)
    private fun validateName(name: String) {
        if (!name.matches(nameRegexp.toRegex())) {
            throw ParameterInvalidException(
                errorCode = ErrorCode.PARAMETER_INVALID,
                message = "카테고리명은 한글/영어 구성만 가능합니다."
            )
        }
    }
}
