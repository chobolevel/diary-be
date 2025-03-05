package com.daangn.api.util

object Regexps {
    val emailRegexp: Regex by lazy {
        "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+\$".toRegex()
    }
    val passwordRegexp: Regex by lazy {
        "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#\$%^&*(),.?\":{}|<>]).{8,}\$".toRegex()
    }
    val nameRegexp: Regex by lazy {
        "^[a-zA-Z0-9가-힣\\s]+\$".toRegex()
    }
}
