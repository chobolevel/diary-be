package com.diary.api.dto.uploads

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UploadResponseDto(
    val filename: String,
    val objectUrl: String,
    val presignedUrl: String,
)
