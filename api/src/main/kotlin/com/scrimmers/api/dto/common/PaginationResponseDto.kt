package com.scrimmers.api.dto.common

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class PaginationResponseDto(
    val skipCount: Long,
    val limitCount: Long,
    val totalCount: Long,
    val data: List<Any>,
)
