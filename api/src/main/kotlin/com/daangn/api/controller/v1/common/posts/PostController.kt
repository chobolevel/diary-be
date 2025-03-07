package com.daangn.api.controller.v1.common.posts

import com.daangn.api.annotation.HasAuthorityUser
import com.daangn.api.dto.common.ResultResponseDto
import com.daangn.api.dto.posts.CreatePostRequestDto
import com.daangn.api.dto.posts.UpdatePostRequestDto
import com.daangn.api.service.posts.PostService
import com.daangn.api.service.posts.query.PostQueryCreator
import com.daangn.api.util.getUserId
import com.daangn.domain.entity.posts.PostOrderType
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

@Tag(name = "Post(게시글)", description = "게시글 관리 API")
@RestController
@RequestMapping("/api/v1")
class PostController(
    private val service: PostService,
    private val queryCreator: PostQueryCreator,
) {

    @Operation(summary = "게시글 등록 API")
    @HasAuthorityUser
    @PostMapping("/posts")
    fun create(
        principal: Principal,
        @Valid @RequestBody
        request: CreatePostRequestDto
    ): ResponseEntity<ResultResponseDto> {
        val result = service.create(
            userId = principal.getUserId(),
            request = request
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(ResultResponseDto(result))
    }

    @Operation(summary = "게시글 목록 조회 API")
    @GetMapping("/posts")
    fun getPosts(
        @RequestParam(required = false) writerId: String?,
        @RequestParam(required = false) categoryId: String?,
        @RequestParam(required = false) title: String?,
        @RequestParam(required = false) page: Long?,
        @RequestParam(required = false) size: Long?,
        @RequestParam(required = false) orderTypes: List<PostOrderType>?
    ): ResponseEntity<ResultResponseDto> {
        val queryFilter = queryCreator.createQueryFilter(
            writerId = writerId,
            categoryId = categoryId,
            title = title,
        )
        val pagination = queryCreator.createPaginationFilter(
            page = page,
            size = size,
        )
        val result = service.getPosts(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes ?: emptyList()
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }

    @Operation(summary = "게시글 단건 조회 API")
    @GetMapping("/posts/{id}")
    fun getPost(@PathVariable("id") postId: String): ResponseEntity<ResultResponseDto> {
        val result = service.getPost(postId)
        return ResponseEntity.ok(ResultResponseDto(result))
    }

    @Operation(summary = "게시글 수정 API")
    @HasAuthorityUser
    @PutMapping("/posts/{id}")
    fun update(
        principal: Principal,
        @PathVariable("id") postId: String,
        @Valid @RequestBody
        request: UpdatePostRequestDto
    ): ResponseEntity<ResultResponseDto> {
        val result = service.update(
            userId = principal.getUserId(),
            postId = postId,
            request = request
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }

    @Operation(summary = "게시글 삭제 API")
    @HasAuthorityUser
    @DeleteMapping("/posts/{id}")
    fun delete(principal: Principal, @PathVariable("id") postId: String): ResponseEntity<ResultResponseDto> {
        val result = service.delete(
            userId = principal.getUserId(),
            postId = postId
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }
}
