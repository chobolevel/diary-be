package com.diary.api.service.uploads.validator

import com.diary.domain.exception.ErrorCode
import com.diary.domain.exception.InvalidParameterException
import org.springframework.stereotype.Component

@Component
class UploadValidator {

    companion object {
        val contentTypes: List<String> = listOfNotNull(
            "image/jpeg",
            "image/png",
            "image/gif",
            "image/webp"
        )
    }

    fun validate(
        contentType: String,
        filename: String,
    ) {
        if (!contentTypes.contains(contentType)) {
            throw InvalidParameterException(
                errorCode = ErrorCode.INVALID_PARAMETER,
                message = "[content_type]은(는) ${contentTypes.joinToString(", ")}만 가능합니다."
            )
        }
    }
}
