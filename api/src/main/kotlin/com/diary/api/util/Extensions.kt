package com.diary.api.util

import com.diary.domain.exception.ErrorCode
import com.diary.domain.exception.InvalidParameterException
import com.diary.domain.type.ID
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import java.security.Principal

fun Principal.getUserId(): ID {
    return this.name
}

fun getCurrentUserId(): ID? {
    val authentication: Authentication = SecurityContextHolder.getContext().authentication
    return if (authentication.isAuthenticated) {
        authentication.getUserId()
    } else {
        null
    }
}

fun String.toSnakeCase(): String {
    return this.map { char ->
        when (char.isUpperCase()) {
            true -> "_${char.lowercase()}"
            false -> "$char"
        }
    }.joinToString("")
}

fun HttpServletRequest.getCookie(key: String): String? {
    if (this.cookies.isNullOrEmpty() || this.cookies.find { it.name == key } == null) {
        return null
    }
    return this.cookies.find { it.name == key }!!.value
}

fun Any?.validateIsNull(parameterName: String) {
    when {
        this is String && this.isEmpty() -> {
            throw InvalidParameterException(
                errorCode = ErrorCode.INVALID_PARAMETER,
                message = "[$parameterName]은(는) 필수 값입니다."
            )
        }
        this == null -> {
            throw InvalidParameterException(
                errorCode = ErrorCode.INVALID_PARAMETER,
                message = "[$parameterName]은(는) 필수 값입니다."
            )
        }
    }
}

fun Int?.validateIsSmallerThan(
    compareTo: Int,
    parameterName: String
) {
    this.validateIsNull(parameterName = parameterName)
    if (this!! < compareTo) {
        throw InvalidParameterException(
            errorCode = ErrorCode.INVALID_PARAMETER,
            message = "[$parameterName]은(는) 반드시 $compareTo 이상이어야 합니다."
        )
    }
}

fun String?.validateLengthSmallerThan(
    length: Int,
    parameterName: String,
) {
    this.validateIsNull(parameterName = parameterName)
    if (this!!.length < length) {
        throw InvalidParameterException(
            errorCode = ErrorCode.INVALID_PARAMETER,
            message = "[$parameterName]은(는) 공백 포함 ${length}자 이상이어야 합니다."
        )
    }
}
