package com.diary.api.controller.v1.common.diaries

import com.diary.api.annotation.HasAuthorityUser
import com.diary.api.dto.common.PaginationResponseDto
import com.diary.api.dto.common.ResultResponseDto
import com.diary.api.service.diaries.DiaryLikeService
import com.diary.api.util.getUserId
import com.diary.domain.dto.Pagination
import com.diary.domain.entity.diaries.likes.DiaryLikeOrderType
import com.diary.domain.entity.diaries.likes.DiaryLikeQueryFilter
import com.diary.domain.type.ID
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@Tag(name = "Diary Like(일기 좋아요)", description = "일기 좋아요 관리 API")
@RestController
@RequestMapping("/api/v1")
class DiaryLikeController(
    private val service: DiaryLikeService
) {

    @Operation(summary = "일기 좋아요 or 좋아요 취소 API")
    @HasAuthorityUser
    @PostMapping("/diaries/{id}/like")
    fun likeOrDislike(
        principal: Principal,
        @PathVariable("id") diaryId: ID
    ): ResponseEntity<ResultResponseDto> {
        val result: Boolean = service.likeOrDislike(
            userId = principal.getUserId(),
            diaryId = diaryId
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }

    @Operation(summary = "일기 좋아요 목록 조회 API")
    @GetMapping("/diaries/likes")
    fun getDiaryLikes(
        @RequestParam(required = false) diaryId: ID?,
        @RequestParam(required = false) userId: ID?,
        @RequestParam(required = false) page: Long?,
        @RequestParam(required = false) size: Long?,
        @RequestParam(required = false) orderTypes: List<DiaryLikeOrderType>?
    ): ResponseEntity<ResultResponseDto> {
        val queryFilter = DiaryLikeQueryFilter(
            diaryId = diaryId,
            userId = userId,
        )
        val pagination = Pagination(
            page = page ?: 1,
            size = size ?: 10
        )
        val result: PaginationResponseDto = service.getDiaryLikes(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes ?: emptyList()
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }
}
