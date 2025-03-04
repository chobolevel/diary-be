package com.daangn.domain.exception

enum class ErrorCode(val message: String) {
    UNKNOWN("알 수 없는 에러"),
    INVALID_PARAMETER("파라미터가 유요하지 않습니다."),
    INVALID_REQUEST_BODY("요청 바디가 유효하지 않습니다."),
    BAD_CREDENTIALS("아이디 또는 비밀번호가 올바르지 않습니다."),
    ACCESS_DENIED("접근 권한이 없습니다."),
    METHOD_NOT_ALLOWED("지원하지 않은 메서드입니다."),

    // USER
    USER_NOT_FOUND("회원을 찾을 수 없습니다."),

    // USER REGION
    USER_REGION_NOT_FOUND("회원 지역을 찾을 수 없습니다.")
}
