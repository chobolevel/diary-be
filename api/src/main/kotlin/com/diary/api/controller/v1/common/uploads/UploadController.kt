package com.diary.api.controller.v1.common.uploads

import com.diary.api.annotation.HasAuthorityAny
import com.diary.api.dto.common.ResultResponseDto
import com.diary.api.dto.uploads.UploadResponseDto
import com.diary.api.service.uploads.UploadService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Upload (업로드)", description = "업로드 관리 API")
@RestController
@RequestMapping("/api/v1")
class UploadController(
    private val service: UploadService
) {

    @Operation(summary = "presigned url 발급 API")
    @HasAuthorityAny
    @GetMapping("/uploads/presigned-url")
    fun generatePresignedUrl(
        @RequestParam(required = true) contentType: String,
        @RequestParam(required = true) filename: String
    ): ResponseEntity<ResultResponseDto> {
        val result: UploadResponseDto = service.generatePresignedUrl(
            contentType = contentType,
            filename = filename
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }
}
