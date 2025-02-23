package com.daangn.domain.exception

enum class ErrorCode(val message: String) {
    UNKNOWN("알 수 없는 에러"),

    // USER
    USER_NOT_FOUND("회원을 찾을 수 없습니다.")
}
