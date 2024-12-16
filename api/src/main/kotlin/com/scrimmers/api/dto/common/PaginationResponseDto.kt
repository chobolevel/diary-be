package com.scrimmers.api.dto.common

data class PaginationResponseDto(
    val skipCount: Long,
    val limitCount: Long,
    val totalCount: Long,
    val data: List<Any>,
)
