package com.diary.api.controller.v1.common.emotions

import com.diary.api.dto.common.PaginationResponseDto
import com.diary.api.dto.common.ResultResponseDto
import com.diary.api.dto.emotions.EmotionResponseDto
import com.diary.api.service.emotions.EmotionService
import com.diary.domain.dto.Pagination
import com.diary.domain.entity.emotions.EmotionOrderType
import com.diary.domain.entity.emotions.EmotionQueryFilter
import com.diary.domain.type.ID
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Emotion(감정)", description = "감정 관리 API")
@RestController
@RequestMapping("/api/v1")
class EmotionController(
    private val service: EmotionService
) {

    @Operation(summary = "감정 목록 조회 API")
    @GetMapping("/emotions")
    fun getEmotions(
        @RequestParam(required = false) name: String?,
        @RequestParam(required = false) page: Long?,
        @RequestParam(required = false) size: Long?,
        @RequestParam(required = false) orderTypes: List<EmotionOrderType>?
    ): ResponseEntity<ResultResponseDto> {
        val queryFilter = EmotionQueryFilter(name = name)
        val pagination = Pagination(
            page = page ?: 1,
            size = size ?: 10
        )
        val result: PaginationResponseDto = service.getEmotions(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes ?: emptyList()
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }

    @Operation(summary = "감정 단건 조회 API")
    @GetMapping("/emotions/{id}")
    fun getEmotion(@PathVariable("id") emotionId: ID): ResponseEntity<ResultResponseDto> {
        val result: EmotionResponseDto = service.getEmotion(emotionId = emotionId)
        return ResponseEntity.ok(ResultResponseDto(result))
    }
}
