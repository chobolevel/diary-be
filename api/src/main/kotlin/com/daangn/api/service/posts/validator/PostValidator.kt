package com.daangn.api.service.posts.validator

import com.daangn.api.dto.posts.CreatePostRequestDto
import com.daangn.api.dto.posts.UpdatePostRequestDto
import com.daangn.api.util.Regexps
import com.daangn.domain.entity.posts.PostUpdateMask
import com.daangn.domain.exception.ErrorCode
import com.daangn.domain.exception.InvalidParameterException
import org.springframework.stereotype.Component

@Component
class PostValidator {

    fun validate(request: CreatePostRequestDto) {
        validateTitle(
            input = request.title,
            parameterName = "title"
        )
        validateLength(
            input = request.content,
            parameterName = "content"
        )
        validatePositiveNumber(
            input = request.salePrice,
            parameterName = "sale_price"
        )
    }

    fun validate(request: UpdatePostRequestDto) {
        request.updateMask.forEach {
            when (it) {
                PostUpdateMask.CATEGORY -> {
                    if (request.categoryId.isNullOrEmpty()) {
                        throw InvalidParameterException(
                            errorCode = ErrorCode.INVALID_PARAMETER,
                            message = "[category_id]은(는) 필수 값입니다."
                        )
                    }
                }

                PostUpdateMask.STATUS -> {
                    if (request.status == null) {
                        throw InvalidParameterException(
                            errorCode = ErrorCode.INVALID_PARAMETER,
                            message = "[status]은(는) 필수 값입니다."
                        )
                    }
                }

                PostUpdateMask.TITLE -> {
                    validateTitle(
                        input = request.title,
                        parameterName = "title"
                    )
                }

                PostUpdateMask.CONTENT -> {
                    validateLength(
                        input = request.content,
                        parameterName = "content"
                    )
                }

                PostUpdateMask.SALE_PRICE -> {
                    if (request.salePrice == null) {
                        throw InvalidParameterException(
                            errorCode = ErrorCode.INVALID_PARAMETER,
                            message = "[sale_price]은(는) 필수 값입니다."
                        )
                    }
                }

                PostUpdateMask.FREE_SHARED -> {
                    if (request.freeShared == null) {
                        throw InvalidParameterException(
                            errorCode = ErrorCode.INVALID_PARAMETER,
                            message = "[free_shared]은(는) 필수 값입니다."
                        )
                    }
                }

                PostUpdateMask.MAIN_IMAGES -> {
                    if (request.mainImages == null) {
                        throw InvalidParameterException(
                            errorCode = ErrorCode.INVALID_PARAMETER,
                            message = "[main_images]은(는) 필수 값입니다."
                        )
                    }
                }
            }
        }
    }

    private fun validateTitle(input: String?, parameterName: String) {
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

    private fun validateLength(input: String?, parameterName: String) {
        if (input.isNullOrEmpty()) {
            throw InvalidParameterException(
                errorCode = ErrorCode.INVALID_PARAMETER,
                message = "[$parameterName]은(는) 필수 값입니다."
            )
        }
        if (input.length < 10) {
            throw InvalidParameterException(
                errorCode = ErrorCode.INVALID_PARAMETER,
                message = "[$parameterName]은(는) 최소 10자 이상이어야 합니다."
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
        if (input < 0) {
            throw InvalidParameterException(
                errorCode = ErrorCode.INVALID_PARAMETER,
                message = "[$parameterName]은(는) 반드시 0 이상이어야 합니다."
            )
        }
    }
}
