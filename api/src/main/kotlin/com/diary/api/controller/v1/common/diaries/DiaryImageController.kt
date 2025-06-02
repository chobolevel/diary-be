package com.diary.api.controller.v1.common.diaries

import com.diary.api.annotation.HasAuthorityUser
import com.diary.api.dto.common.ResultResponseDto
import com.diary.api.dto.diaries.images.CreateDiaryImageRequestDto
import com.diary.api.dto.diaries.images.UpdateDiaryImageRequestDto
import com.diary.api.service.diaries.DiaryImageService
import com.diary.api.util.getUserId
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
import java.security.Principal

@Tag(name = "Diary Image(일기 이미지)", description = "일기 이미지 관리 API")
@RestController
@RequestMapping("/api/v1")
class DiaryImageController(
    private val service: DiaryImageService
) {

    @Operation(summary = "일기 이미지 등록 API")
    @HasAuthorityUser
    @PostMapping("/diaries/{diaryId}/images")
    fun create(
        principal: Principal,
        @PathVariable diaryId: ID,
        @Valid @RequestBody
        request: CreateDiaryImageRequestDto
    ): ResponseEntity<ResultResponseDto> {
        val result: ID = service.create(
            userId = principal.getUserId(),
            diaryId = diaryId,
            request = request
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(ResultResponseDto(result))
    }

    @Operation(summary = "일기 이미지 수정 API")
    @HasAuthorityUser
    @PutMapping("/diaries/{diaryId}/images/{diaryImageId}")
    fun update(
        principal: Principal,
        @PathVariable diaryId: ID,
        @PathVariable diaryImageId: ID,
        @Valid @RequestBody
        request: UpdateDiaryImageRequestDto
    ): ResponseEntity<ResultResponseDto> {
        val result: ID = service.update(
            userId = principal.getUserId(),
            diaryId = diaryId,
            diaryImageId = diaryImageId,
            request = request
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }

    @Operation(summary = "일기 이미지 삭제 API")
    @HasAuthorityUser
    @DeleteMapping("/diaries/{diaryId}/images/{diaryImageId}")
    fun delete(
        principal: Principal,
        @PathVariable diaryId: ID,
        @PathVariable diaryImageId: ID
    ): ResponseEntity<ResultResponseDto> {
        val result: Boolean = service.delete(
            userId = principal.getUserId(),
            diaryId = diaryId,
            diaryImageId = diaryImageId
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }
}
