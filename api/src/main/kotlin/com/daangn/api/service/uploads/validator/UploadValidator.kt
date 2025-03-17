package com.daangn.api.service.uploads.validator

import com.daangn.api.dto.uploads.UploadRequestDto
import com.daangn.domain.exception.ErrorCode
import com.daangn.domain.exception.InvalidParameterException
import org.springframework.stereotype.Component

@Component
class UploadValidator {

    private final val availablePrefixList = listOf("image")
    private final val availableExtensionList = listOf("jpg", "jpeg", "png", "gif")

    fun validate(request: UploadRequestDto) {
        if (!availablePrefixList.contains(request.prefix)) {
            throw InvalidParameterException(
                errorCode = ErrorCode.INVALID_PARAMETER,
                message = "${availablePrefixList.joinToString(", ")} 파일(prefix)의 업로드만 지원합니다."
            )
        }
        val filenameLastDotIdx: Int = request.filename.lastIndexOf(".")
        if (filenameLastDotIdx < 0) {
            throw InvalidParameterException(
                errorCode = ErrorCode.INVALID_PARAMETER,
                message = "[filename]의 파일명이 올바르지 않습니다."
            )
        }
        if (!availableExtensionList.contains(request.filename.substring(filenameLastDotIdx + 1))) {
            throw InvalidParameterException(
                errorCode = ErrorCode.INVALID_PARAMETER,
                message = "${availableExtensionList.joinToString(", ")} 확장자 파일의 업로드만 지원합니다."
            )
        }
    }
}
