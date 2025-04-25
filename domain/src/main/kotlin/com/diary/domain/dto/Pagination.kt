package com.diary.domain.dto

data class Pagination(
    val page: Long,
    val size: Long
) {
    val offset: Long = (page - 1) * size
    val limit: Long = size
}
