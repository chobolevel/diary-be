package com.daangn.api.controller.v1.common.posts

import com.daangn.api.annotation.HasAuthorityUser
import com.daangn.api.dto.common.ResultResponseDto
import com.daangn.api.service.posts.PostLikeService
import com.daangn.api.service.posts.query.PostLikeQueryCreator
import com.daangn.api.util.getUserId
import com.daangn.domain.entity.posts.likes.PostLikeOrderType
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

@Tag(name = "PostLike(게시글 좋아요)", description = "게시글 좋아요 관리 API")
@RestController
@RequestMapping("/api/v1")
class PostLikeController(
    private val service: PostLikeService,
    private val queryCreator: PostLikeQueryCreator
) {

    @Operation(summary = "게시글 좋아요 API")
    @HasAuthorityUser
    @PostMapping("/posts/{id}/likes")
    fun likeOrUnlike(
        principal: Principal,
        @PathVariable("id") postId: String
    ): ResponseEntity<ResultResponseDto> {
        val result = service.likeOrUnlike(
            userId = principal.getUserId(),
            postId = postId
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }

    @Operation(summary = "게시글 좋아요 목록 조회 API")
    @GetMapping("/post/likes")
    fun getPostLikes(
        @RequestParam(required = false) postId: String?,
        @RequestParam(required = false) userId: String?,
        @RequestParam(required = false) page: Long?,
        @RequestParam(required = false) size: Long?,
        @RequestParam(required = false) orderTypes: List<PostLikeOrderType>?
    ): ResponseEntity<ResultResponseDto> {
        val queryFilter = queryCreator.createQueryFilter(
            postId = postId,
            userId = userId
        )
        val pagination = queryCreator.createPaginationFilter(
            page = page,
            size = size
        )
        val result = service.getPostLikes(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes ?: emptyList()
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }
}
