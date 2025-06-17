package com.diary.api.controller.v1.common.products

import com.diary.api.dto.common.PaginationResponseDto
import com.diary.api.dto.common.ResultResponseDto
import com.diary.api.dto.products.ProductResponseDto
import com.diary.api.service.products.ProductService
import com.diary.domain.dto.Pagination
import com.diary.domain.entity.products.ProductOrderType
import com.diary.domain.entity.products.ProductQueryFilter
import com.diary.domain.entity.products.ProductStatus
import com.diary.domain.type.ID
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Product(상품)", description = "상품 관리 API")
@RestController
@RequestMapping("/api/v1")
class ProductController(
    private val service: ProductService
) {

    @Operation(summary = "상품 목록 조회 API")
    @GetMapping("/products")
    fun getProducts(
        @RequestParam(required = false) productCategoryId: ID?,
        @RequestParam(required = false) status: ProductStatus?,
        @RequestParam(required = false) page: Long?,
        @RequestParam(required = false) size: Long?,
        @RequestParam(required = false) orderTypes: List<ProductOrderType>?
    ): ResponseEntity<ResultResponseDto> {
        val queryFilter = ProductQueryFilter(
            productCategoryId = productCategoryId,
            status = status
        )
        val pagination = Pagination(
            page = page ?: 1,
            size = size ?: 10
        )
        val result: PaginationResponseDto = service.getProducts(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes ?: emptyList()
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }

    @Operation(summary = "상품 단건 조회 API")
    @GetMapping("/products/{productId}")
    fun getProduct(@PathVariable productId: ID): ResponseEntity<ResultResponseDto> {
        val result: ProductResponseDto = service.getProduct(productId = productId)
        return ResponseEntity.ok(ResultResponseDto(result))
    }
}
