package com.daangn.api.util

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
