package com.diary.api.controller.v1.admin.emotions

import com.diary.api.annotation.HasAuthorityAdmin
import com.diary.api.dto.common.ResultResponseDto
import com.diary.api.dto.emotions.CreateEmotionRequestDto
import com.diary.api.dto.emotions.UpdateEmotionRequestDto
import com.diary.api.service.emotions.EmotionService
import com.diary.domain.type.ID
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Emotion(관리자 감정)", description = "관리자 감정 관리 API")
@RestController
@RequestMapping("/api/v1/admin")
class AdminEmotionController(
    private val service: EmotionService
) {

    @Operation(summary = "감정 등록 API")
    @HasAuthorityAdmin
    @PostMapping("/emotions")
    fun create(
        @Valid @RequestBody
        request: CreateEmotionRequestDto
    ): ResponseEntity<ResultResponseDto> {
        val result: ID = service.create(request = request)
        return ResponseEntity.status(HttpStatus.CREATED).body(ResultResponseDto(result))
    }

    @Operation(summary = "감정 정보 수정 API")
    @HasAuthorityAdmin
    @PutMapping("/emotions/{id}")
    fun update(
        @PathVariable("id") emotionId: ID,
        @Valid @RequestBody
        request: UpdateEmotionRequestDto
    ): ResponseEntity<ResultResponseDto> {
        val result: ID = service.update(
            emotionId = emotionId,
            request = request
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }

    @Operation(summary = "감정 정보 삭제 API")
    @HasAuthorityAdmin
    @DeleteMapping("/emotions/{id}")
    fun delete(@PathVariable("id") emotionId: ID): ResponseEntity<ResultResponseDto> {
        val result: Boolean = service.delete(emotionId = emotionId)
        return ResponseEntity.ok(ResultResponseDto(result))
    }
}
