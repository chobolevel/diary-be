package com.diary.api.controller.v1.admin.products

import com.diary.api.annotation.HasAuthorityAdmin
import com.diary.api.dto.common.ResultResponseDto
import com.diary.api.dto.products.categories.CreateProductCategoryRequestDto
import com.diary.api.dto.products.categories.UpdateProductCategoryRequestDto
import com.diary.api.service.products.ProductCategoryService
import com.diary.domain.type.ID
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Product Category(상품 카테고리)", description = "관리자 상품 카테고리 관리 API")
@RestController
@RequestMapping("/api/v1/admin")
class AdminProductCategoryController(
    private val service: ProductCategoryService
) {

    @Operation(summary = "상품 카테고리 등록 API")
    @HasAuthorityAdmin
    @PostMapping("/product-categories")
    fun create(
        @Valid @RequestBody
        request: CreateProductCategoryRequestDto
    ): ResponseEntity<ResultResponseDto> {
        val result: ID = service.create(request = request)
        return ResponseEntity.status(HttpStatus.CREATED).body(ResultResponseDto(result))
    }

    @Operation(summary = "상품 카테고리 수정 API")
    @HasAuthorityAdmin
    @PutMapping("/product-categories/{productCategoryId}")
    fun update(
        @PathVariable productCategoryId: ID,
        @Valid @RequestBody
        request: UpdateProductCategoryRequestDto
    ): ResponseEntity<ResultResponseDto> {
        val result: ID = service.update(
            productCategoryId = productCategoryId,
            request = request
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }

    @Operation(summary = "상품 카테고리 삭제 API")
    @HasAuthorityAdmin
    @DeleteMapping("/product-categories/{productCategoryId}")
    fun delete(@PathVariable productCategoryId: ID): ResponseEntity<ResultResponseDto> {
        val result: Boolean = service.delete(productCategoryId = productCategoryId)
        return ResponseEntity.ok(ResultResponseDto(result))
    }
}
