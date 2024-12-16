package com.scrimmers.api

import java.security.Principal

fun Principal.getUserId(): String {
    return this.name
}
