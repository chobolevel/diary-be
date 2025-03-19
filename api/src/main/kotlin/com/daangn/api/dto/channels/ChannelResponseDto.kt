package com.daangn.api.dto.channels

import com.daangn.api.dto.users.UserResponseDto
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ChannelResponseDto(
    val id: String,
    val name: String,
    val channelUsers: List<UserResponseDto>,
    val createdAt: Long,
    val updatedAt: Long,
)
