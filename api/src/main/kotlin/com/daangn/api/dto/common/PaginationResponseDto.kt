package com.daangn.api.dto.common

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class PaginationResponseDto(
    val page: Long,
    val size: Long,
    val data: List<Any>,
    val totalCount: Long
)
