package com.daangn.api.dto.uploads

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import jakarta.validation.constraints.NotEmpty

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UploadRequestDto(
    @field:NotEmpty
    val prefix: String,
    @field:NotEmpty
    val filename: String,
)
