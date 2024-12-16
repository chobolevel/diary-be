package com.scrimmers.domain

data class Pagination(
    val offset: Long,
    val limit: Long
)

data class Common(
    val name: String
)
