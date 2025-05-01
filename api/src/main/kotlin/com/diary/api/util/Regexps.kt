package com.diary.api.util

object Regexps {
    val usernameRegex: Regex by lazy {
        "^[a-zA-Z0-9]{6,}\$".toRegex()
    }
    val passwordRegexp: Regex by lazy {
        "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#\$]).{8,}\$".toRegex()
    }
    val nicknameRegexp: Regex by lazy {
        "^[a-zA-Z가-힣0-9]+\$".toRegex()
    }
}
