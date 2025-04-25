package com.diary.api.util

import jakarta.servlet.http.HttpServletRequest
import java.security.Principal

fun Principal.getUserId(): String {
    return this.name
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
