package com.diary.api.controller.v1.common.diaries

import com.diary.api.annotation.HasAuthorityUser
import com.diary.api.dto.common.PaginationResponseDto
import com.diary.api.dto.common.ResultResponseDto
import com.diary.api.dto.diaries.CreateDiaryRequestDto
import com.diary.api.dto.diaries.DiaryResponseDto
import com.diary.api.dto.diaries.UpdateDiaryRequestDto
import com.diary.api.service.diaries.DiaryService
import com.diary.api.util.getUserId
import com.diary.domain.dto.Pagination
import com.diary.domain.entity.diaries.DiaryOrderType
import com.diary.domain.entity.diaries.DiaryQueryFilter
import com.diary.domain.type.ID
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@Tag(name = "Diary(일기)", description = "일기 관리 API")
@RestController
@RequestMapping("/api/v1")
class DiaryController(
    private val service: DiaryService
) {

    @Operation(summary = "일기 등록 API")
    @HasAuthorityUser
    @PostMapping("/diaries")
    fun create(
        principal: Principal,
        @Valid @RequestBody
        request: CreateDiaryRequestDto
    ): ResponseEntity<ResultResponseDto> {
        val result: ID = service.create(
            userId = principal.getUserId(),
            request = request
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(ResultResponseDto(result))
    }

    @Operation(summary = "일기 목록 조회 API")
    @GetMapping("/diaries")
    fun getDiaries(
        @RequestParam(required = false) writerId: ID?,
        @RequestParam(required = false) weatherId: ID?,
        @RequestParam(required = false) emotionId: ID?,
        @RequestParam(required = false) title: String?,
        @RequestParam(required = false) page: Long?,
        @RequestParam(required = false) size: Long?,
        @RequestParam(required = false) orderTypes: List<DiaryOrderType>?
    ): ResponseEntity<ResultResponseDto> {
        val queryFilter = DiaryQueryFilter(
            writerId = writerId,
            weatherId = weatherId,
            emotionId = emotionId,
            title = title,
        )
        val pagination = Pagination(
            page = page ?: 1,
            size = size ?: 10
        )
        val result: PaginationResponseDto = service.getDiaries(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes ?: emptyList()
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }

    @Operation(summary = "일기 단건 조회 API")
    @GetMapping("/diaries/{id}")
    fun getDiary(@PathVariable("id") diaryId: ID): ResponseEntity<ResultResponseDto> {
        val result: DiaryResponseDto = service.getDiary(diaryId = diaryId)
        return ResponseEntity.ok(ResultResponseDto(result))
    }

    @Operation(summary = "일기 정보 수정 API")
    @HasAuthorityUser
    @PutMapping("/diaries/{id}")
    fun update(
        principal: Principal,
        @PathVariable("id") diaryId: ID,
        @Valid @RequestBody
        request: UpdateDiaryRequestDto
    ): ResponseEntity<ResultResponseDto> {
        val result: ID = service.update(
            userId = principal.getUserId(),
            diaryId = diaryId,
            request = request
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }

    @Operation(summary = "일기 정보 삭제 API")
    @HasAuthorityUser
    @DeleteMapping("/diaries/{id}")
    fun delete(
        principal: Principal,
        @PathVariable("id") diaryId: ID
    ): ResponseEntity<ResultResponseDto> {
        val result: Boolean = service.delete(
            userId = principal.getUserId(),
            diaryId = diaryId
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }
}
