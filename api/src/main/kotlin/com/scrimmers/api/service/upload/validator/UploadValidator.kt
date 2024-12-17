package com.scrimmers.api.service.upload.validator

import com.scrimmers.api.dto.upload.UploadRequestDto
import com.scrimmers.domain.exception.ErrorCode
import com.scrimmers.domain.exception.ParameterInvalidException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class UploadValidator {

    private final val availablePrefixList = listOf("image")
    private final val availableExtensionList = listOf("jpg", "jpeg", "png", "gif")

    fun validate(request: UploadRequestDto) {
        if (!availablePrefixList.contains(request.prefix)) {
            throw ParameterInvalidException(
                errorCode = ErrorCode.PARAMETER_INVALID,
                status = HttpStatus.BAD_REQUEST,
                message = "${availablePrefixList.joinToString(", ")} 파일(prefix)의 업로드만 지원합니다."
            )
        }
        if (!availableExtensionList.contains(request.extension)) {
            throw ParameterInvalidException(
                errorCode = ErrorCode.PARAMETER_INVALID,
                status = HttpStatus.BAD_REQUEST,
                message = "${availableExtensionList.joinToString(", ")} 확장자 파일의 업로드만 지원합니다."
            )
        }
    }
}
