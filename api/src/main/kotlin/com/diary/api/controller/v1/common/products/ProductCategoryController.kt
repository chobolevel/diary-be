package com.diary.api.controller.v1.common.products

import com.diary.api.dto.common.PaginationResponseDto
import com.diary.api.dto.common.ResultResponseDto
import com.diary.api.dto.products.categories.ProductCategoryResponseDto
import com.diary.api.service.products.ProductCategoryService
import com.diary.domain.dto.Pagination
import com.diary.domain.entity.products.categories.ProductCategoryOrderType
import com.diary.domain.entity.products.categories.ProductCategoryQueryFilter
import com.diary.domain.type.ID
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Product Category(상품 카테고리)", description = "상품 카테고리 관리 API")
@RestController
@RequestMapping("/api/v1")
class ProductCategoryController(
    private val service: ProductCategoryService
) {

    @Operation(summary = "상품 카테고리 목록 조회 API")
    @GetMapping("/product-categories")
    fun getProductCategories(
        @RequestParam(required = false) name: String?,
        @RequestParam(required = false) page: Long?,
        @RequestParam(required = false) size: Long?,
        @RequestParam(required = false) orderTypes: List<ProductCategoryOrderType>?
    ): ResponseEntity<ResultResponseDto> {
        val queryFilter = ProductCategoryQueryFilter(
            name = name
        )
        val pagination = Pagination(
            page = page ?: 1,
            size = size ?: 10
        )
        val result: PaginationResponseDto = service.getProductCategories(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes ?: emptyList()
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }

    @Operation(summary = "상품 카테고리 단건 조회 API")
    @GetMapping("/product-categories/{productCategoryId}")
    fun getProductCategory(@PathVariable productCategoryId: ID): ResponseEntity<ResultResponseDto> {
        val result: ProductCategoryResponseDto = service.getProductCategory(productCategoryId = productCategoryId)
        return ResponseEntity.ok(ResultResponseDto(result))
    }
}
