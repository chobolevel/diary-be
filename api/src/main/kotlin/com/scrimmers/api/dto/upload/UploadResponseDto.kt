package com.scrimmers.api.dto.upload

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UploadResponseDto(
    val presignedUrl: String,
    val url: String,
    val filenameWithExtension: String,
)
