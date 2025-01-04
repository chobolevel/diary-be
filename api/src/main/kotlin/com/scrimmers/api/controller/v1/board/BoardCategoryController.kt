package com.scrimmers.api.controller.v1.board

import com.scrimmers.api.annotation.HasAuthorityAdmin
import com.scrimmers.api.dto.board.category.CreateBoardCategoryRequestDto
import com.scrimmers.api.dto.board.category.UpdateBoardCategoryRequestDto
import com.scrimmers.api.dto.common.ResultResponse
import com.scrimmers.api.service.board.BoardCategoryService
import com.scrimmers.api.service.board.query.BoardCategoryQueryCreator
import com.scrimmers.domain.entity.borad.category.BoardCategoryOrderType
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
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

@Tag(name = "Board Category(게시판 카테고리)", description = "게시판 카테고리 관리 API")
@RestController
@RequestMapping("/api/v1")
class BoardCategoryController(
    private val service: BoardCategoryService,
    private val queryCreator: BoardCategoryQueryCreator
) {

    @Operation(summary = "게시판 카테고리 등록 API")
    @HasAuthorityAdmin
    @PostMapping("/boards/categories")
    fun createBoardCategory(
        @Valid @RequestBody
        request: CreateBoardCategoryRequestDto
    ): ResponseEntity<ResultResponse> {
        val result = service.create(request)
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "게시판 카테고리 목록 조회 API")
    @GetMapping("/boards/categories")
    fun getBoardCategories(
        @RequestParam(required = false) code: String?,
        @RequestParam(required = false) name: String?,
        @RequestParam(required = false) skipCount: Long?,
        @RequestParam(required = false) limitCount: Long?,
        @RequestParam(required = false) orderTypes: List<BoardCategoryOrderType>?
    ): ResponseEntity<ResultResponse> {
        val queryFilter = queryCreator.createQueryFilter(
            code = code,
            name = name,
        )
        val pagination = queryCreator.createPaginationFilter(
            skipCount = skipCount,
            limitCount = limitCount,
        )
        val result = service.getBoardCategories(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes
        )
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "게시판 카테고리 단건 조회 API")
    @GetMapping("/boards/categories/{id}")
    fun getBoardCategory(@PathVariable("id") boardCategoryId: String): ResponseEntity<ResultResponse> {
        val result = service.getBoardCategory(boardCategoryId)
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "게시판 카테고리 수정 API")
    @HasAuthorityAdmin
    @PutMapping("/boards/categories/{id}")
    fun updateBoardCategory(
        @PathVariable("id") boardCategoryId: String,
        @Valid @RequestBody
        request: UpdateBoardCategoryRequestDto
    ): ResponseEntity<ResultResponse> {
        val result = service.update(
            boardCategoryId = boardCategoryId,
            request = request
        )
        return ResponseEntity.ok(ResultResponse((result)))
    }

    @Operation(summary = "게시판 카테고리 삭제 API")
    @HasAuthorityAdmin
    @DeleteMapping("/boards/categories/{id}")
    fun deleteBoardCategory(@PathVariable("id") boardCategoryId: String): ResponseEntity<ResultResponse> {
        val result = service.delete(boardCategoryId)
        return ResponseEntity.ok(ResultResponse(result))
    }
}
