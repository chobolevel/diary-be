package com.daangn.api.dto.uploads

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UploadResponseDto(
    val presignedUrl: String,
    val url: String,
    val filename: String
)
