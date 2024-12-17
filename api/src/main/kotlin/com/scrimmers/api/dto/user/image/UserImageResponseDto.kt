package com.scrimmers.api.dto.user.image

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.scrimmers.domain.entity.user.image.UserImageType

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UserImageResponseDto(
    val id: String,
    val type: UserImageType,
    val name: String,
    val url: String,
    val createdAt: Long,
    val updatedAt: Long
)
