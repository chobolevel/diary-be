package com.daangn.domain.dto

data class Pagination(
    val page: Long,
    val size: Long
) {
    val offset = (page - 1) * size
    val limit = size
}
