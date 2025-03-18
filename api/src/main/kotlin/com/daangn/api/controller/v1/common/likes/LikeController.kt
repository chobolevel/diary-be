package com.daangn.api.controller.v1.common.likes

import com.daangn.api.annotation.HasAuthorityUser
import com.daangn.api.dto.common.ResultResponseDto
import com.daangn.api.dto.likes.LikeRequestDto
import com.daangn.api.service.likes.LikeService
import com.daangn.api.service.likes.query.LikeQueryCreator
import com.daangn.api.util.getUserId
import com.daangn.domain.entity.likes.LikeOrderType
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@Tag(name = "Like(좋아요)", description = "좋아요 관리 API")
@RestController
@RequestMapping("/api/v1")
class LikeController(
    private val service: LikeService,
    private val queryCreator: LikeQueryCreator
) {

    @Operation(summary = "좋아요/좋아요 제거 API")
    @HasAuthorityUser
    @PostMapping("/likes")
    fun likeOrUnlike(
        principal: Principal,
        @Valid @RequestBody
        request: LikeRequestDto
    ): ResponseEntity<ResultResponseDto> {
        val result: Boolean = service.likeOrUnlike(
            userId = principal.getUserId(),
            request = request
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }

    @Operation(summary = "좋아요 목록 조회 API")
    @HasAuthorityUser
    @GetMapping("/likes")
    fun getLikes(
        principal: Principal,
        @RequestParam(required = false) page: Long?,
        @RequestParam(required = false) size: Long?,
        @RequestParam(required = false) orderTypes: List<LikeOrderType>?
    ): ResponseEntity<ResultResponseDto> {
        val queryFilter = queryCreator.createQueryFilter(
            userId = principal.getUserId()
        )
        val pagination = queryCreator.createPaginationFilter(
            page = page,
            size = size
        )
        val result = service.getLikes(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes ?: emptyList()
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }
}
