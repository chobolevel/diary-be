package com.scrimmers.api.service.user.validator

import com.scrimmers.api.dto.user.image.UpdateUserImageRequestDto
import com.scrimmers.domain.entity.user.image.UserImageUpdateMask
import com.scrimmers.domain.exception.ErrorCode
import com.scrimmers.domain.exception.ParameterInvalidException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class UserImageValidator {

    fun validate(request: UpdateUserImageRequestDto) {
        request.updateMask.forEach {
            when (it) {
                UserImageUpdateMask.TYPE -> {
                    if (request.type == null) {
                        throw ParameterInvalidException(
                            errorCode = ErrorCode.PARAMETER_INVALID,
                            status = HttpStatus.BAD_REQUEST,
                            message = "변경할 이미지 타입이 유효하지 않습니다."
                        )
                    }
                }

                UserImageUpdateMask.NAME -> {
                    if (request.name.isNullOrEmpty()) {
                        throw ParameterInvalidException(
                            errorCode = ErrorCode.PARAMETER_INVALID,
                            status = HttpStatus.BAD_REQUEST,
                            message = "변경할 이미지 파일명이 유효하지 않습니다."
                        )
                    }
                }

                UserImageUpdateMask.URL -> {
                    if (request.url.isNullOrEmpty()) {
                        throw ParameterInvalidException(
                            errorCode = ErrorCode.PARAMETER_INVALID,
                            status = HttpStatus.BAD_REQUEST,
                            message = "변경할 이미지 경로가 유효하지 않습니다."
                        )
                    }
                }
            }
        }
    }
}
