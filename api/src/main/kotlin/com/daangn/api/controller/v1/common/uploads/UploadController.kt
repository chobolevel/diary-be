package com.daangn.api.controller.v1.common.uploads

import com.daangn.api.annotation.HasAuthorityAny
import com.daangn.api.dto.common.ResultResponseDto
import com.daangn.api.dto.uploads.UploadRequestDto
import com.daangn.api.service.uploads.UploadService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Upload(파일 업로드)", description = "파일 업로드 관리 API")
@RestController
@RequestMapping("/api/v1")
class UploadController(
    private val service: UploadService
) {

    @Operation(summary = "S3 버킷 파일 업로드를 위한 경로 발급 API")
    @HasAuthorityAny
    @PostMapping("/uploads/presigned-url")
    fun issuePresignedUrl(
        @Valid @RequestBody
        request: UploadRequestDto
    ): ResponseEntity<ResultResponseDto> {
        val result = service.getPresignedUrl(request = request)
        return ResponseEntity.status(HttpStatus.CREATED).body(ResultResponseDto(result))
    }
}
