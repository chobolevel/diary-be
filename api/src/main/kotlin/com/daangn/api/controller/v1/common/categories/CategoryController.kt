package com.daangn.api.controller.v1.common.categories

import com.daangn.api.dto.common.ResultResponseDto
import com.daangn.api.service.categories.CategoryService
import com.daangn.api.service.categories.query.CategoryQueryCreator
import com.daangn.domain.entity.categories.CategoryOrderType
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Category(카테고리)", description = "카테고리 관리 API")
@RestController
@RequestMapping("/api/v1")
class CategoryController(
    private val service: CategoryService,
    private val queryCreator: CategoryQueryCreator
) {

    @Operation(summary = "카테고리 목록 조회 API")
    @GetMapping("/categories")
    fun getCategories(
        @RequestParam(required = false) name: String?,
        @RequestParam(required = false) page: Long?,
        @RequestParam(required = false) size: Long?,
        @RequestParam(required = false) orderTypes: List<CategoryOrderType>?
    ): ResponseEntity<ResultResponseDto> {
        val queryFilter = queryCreator.createQueryFilter(
            name = name
        )
        val pagination = queryCreator.createPaginationFilter(
            page = page,
            size = size
        )
        val result = service.getCategories(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes ?: emptyList()
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }

    @Operation(summary = "카테고리 단건 조회 API")
    @GetMapping("/categories/{id}")
    fun getCategory(@PathVariable("id") categoryId: String): ResponseEntity<ResultResponseDto> {
        val result = service.getCategory(categoryId)
        return ResponseEntity.ok(ResultResponseDto(result))
    }
}
