package com.scrimmers.api.controller.v1.board

import com.scrimmers.api.annotation.HasAuthorityUser
import com.scrimmers.api.dto.board.CreateBoardRequestDto
import com.scrimmers.api.dto.board.UpdateBoardRequestDto
import com.scrimmers.api.dto.common.ResultResponse
import com.scrimmers.api.getUserId
import com.scrimmers.api.service.board.BoardService
import com.scrimmers.api.service.board.query.BoardQueryCreator
import com.scrimmers.domain.entity.borad.BoardOrderType
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
import java.security.Principal

@Tag(name = "Board (게시글)", description = "게시글 관리 API")
@RestController
@RequestMapping("/api/v1")
class BoardController(
    private val service: BoardService,
    private val queryCreator: BoardQueryCreator
) {

    @Operation(summary = "게시글 등록 API")
    @HasAuthorityUser
    @PostMapping("/boards")
    fun createBoard(
        principal: Principal,
        @Valid @RequestBody
        request: CreateBoardRequestDto
    ): ResponseEntity<ResultResponse> {
        val result = service.create(
            userId = principal.getUserId(),
            request = request
        )
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "게시글 목록 조회 API")
    @GetMapping("/boards")
    fun getBoards(
        @RequestParam(required = false) writerId: String?,
        @RequestParam(required = false) boardCategoryId: String?,
        @RequestParam(required = false) title: String?,
        @RequestParam(required = false) skipCount: Long?,
        @RequestParam(required = false) limitCount: Long?,
        @RequestParam(required = false) orderTypes: List<BoardOrderType>?
    ): ResponseEntity<ResultResponse> {
        val queryFilter = queryCreator.createQueryFilter(
            writerId = writerId,
            boardCategoryId = boardCategoryId,
            title = title,
        )
        val pagination = queryCreator.createPaginationFilter(
            skipCount = skipCount,
            limitCount = limitCount,
        )
        val result = service.getBoards(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes
        )
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "게시글 단건 조회 API")
    @GetMapping("/boards/{id}")
    fun getBoard(@PathVariable("id") boardId: String): ResponseEntity<ResultResponse> {
        val result = service.getBoard(boardId)
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "게시글 수정 API")
    @HasAuthorityUser
    @PutMapping("/boards/{id}")
    fun updateBoard(
        principal: Principal,
        @PathVariable("id") boardId: String,
        @Valid @RequestBody
        request: UpdateBoardRequestDto
    ): ResponseEntity<ResultResponse> {
        val result = service.update(
            userId = principal.getUserId(),
            boardId = boardId,
            request = request
        )
        return ResponseEntity.ok(ResultResponse(result))
    }

    @Operation(summary = "게시글 삭제 API")
    @HasAuthorityUser
    @DeleteMapping("/boards/{id}")
    fun deleteBoard(principal: Principal, @PathVariable("id") boardId: String): ResponseEntity<ResultResponse> {
        val result = service.delete(
            userId = principal.getUserId(),
            boardId = boardId
        )
        return ResponseEntity.ok(ResultResponse(result))
    }
}
