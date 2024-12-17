package com.scrimmers.domain.exception

enum class ErrorCode(val desc: String) {
    UNKNOWN_ERROR("알 수 없는 에러입니다."),
    PARAMETER_INVALID("잘못된 파라미터입니다."),

    // AUTH
    BAD_CREDENTIALS("사용자 인증에 실패하였습니다."),

    // JWT
    EXPIRED_TOKEN("만료된 토큰입니다."),
    INVALID_TOKEN("유효하지 않은 토큰입니다."),

    // USER
    USER_IS_NOT_FOUND("회원을 찾을 수 없습니다."),
    USER_EMAIL_IS_INCORRECT_FORMAT("이메일 형식이 올바르지 않습니다."),
    USER_EMAIL_IS_ALREADY_EXISTS("이미 존재하는 이메일입니다."),
    USER_NICKNAME_IS_INCORRECT_FORMAT("닉네임 형식이 올바르지 않습니다.(한글 2~20자)"),
    USER_NICKNAME_IS_ALREADY_EXISTS("이미 존재하는 닉네임입니다."),
    USER_PHONE_IS_INCORRECT_FORMAT("전화번호 형식이 올바르지 않습니다.(하이픈 제외)"),
    USER_PHONE_IS_ALREADY_EXISTS("이미 존재하는 전화번호입니다."),

    // USER IMAGE
    USER_IMAGE_IS_NOT_FOUND("회원 이미지를 찾을 수 없습니다."),

    // TEAM
    TEAM_IS_NOT_FOUND("팁을 찾을 수 없습니다."),
    NO_ACCESS_EXCEPT_FOR_OWNER("팁장 외 접근 불가"),

    // TEAM IMAGE
    TEAM_IMAGE_IS_NOT_FOUND("팀 이미지를 찾을 수 없습니다."),
}
