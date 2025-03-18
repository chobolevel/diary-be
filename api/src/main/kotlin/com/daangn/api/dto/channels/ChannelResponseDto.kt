package com.daangn.api.dto.channels

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ChannelResponseDto(
    val id: String,
    val name: String,
    val createdAt: Long,
    val updatedAt: Long,
)
