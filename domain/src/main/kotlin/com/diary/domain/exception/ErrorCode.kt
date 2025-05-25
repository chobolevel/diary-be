package com.diary.domain.exception

enum class ErrorCode(val message: String) {
    UNKNOWN("알 수 없는 에러"),
    INVALID_PARAMETER("파라미터가 유요하지 않습니다."),
    INVALID_REQUEST_BODY("요청 바디가 유효하지 않습니다."),
    BAD_CREDENTIALS("아이디 또는 비밀번호가 올바르지 않습니다."),
    ACCESS_DENIED("접근 권한이 없습니다."),
    METHOD_NOT_ALLOWED("지원하지 않은 메서드입니다."),
    EXPIRED_TOKEN("토큰이 만료되었습니다."),
    INVALID_TOKEN("토큰이 유효하지 않습니다."),
    INVALID_REFRESH_TOKEN("리프래시 토큰이 유효하지 않습니다."),
    REFRESH_TOKEN_NOT_EXISTS("리프래시 토큰이 존재하지 않습니다."),

    // USER
    USER_NOT_FOUND("회원을 찾을 수 없습니다."),
    USER_CURRENT_PASSWORD_NOT_MATCH("현재 비밀번호가 일치하지 않습니다."),

    // WEATHER
    WEATHER_NOT_FOUND("날씨 정보를 찾을 수 없습니다."),

    // EMOTION
    EMOTION_NOT_FOUND("감정 정보를 찾을 수 없습니다."),

    // DIARY
    DIARY_NOT_FOUND("일기를 찾을 수 없습니다."),
    WRITER_ONLY_ACCESS("작성자 외 접근 금지"),

    // DIARY LIKE
    DIARY_LIKE_NOT_FOUND("일기의 좋아요을 찾을 수 없습니다.")
}
