package com.diary.api.controller.v1.admin.products

import com.diary.api.annotation.HasAuthorityAdmin
import com.diary.api.dto.common.ResultResponseDto
import com.diary.api.dto.products.CreateProductRequestDto
import com.diary.api.dto.products.UpdateProductRequestDto
import com.diary.api.service.products.ProductService
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

@Tag(name = "Product(상품)", description = "관리자 상품 관리 API")
@RestController
@RequestMapping("/api/v1/admin")
class AdminProductController(
    private val service: ProductService
) {

    @Operation(summary = "상품 등록 API")
    @HasAuthorityAdmin
    @PostMapping("/products")
    fun create(
        @Valid @RequestBody
        request: CreateProductRequestDto
    ): ResponseEntity<ResultResponseDto> {
        val result: ID = service.create(request = request)
        return ResponseEntity.status(HttpStatus.CREATED).body(ResultResponseDto(result))
    }

    @Operation(summary = "상품 정보 수정 API")
    @HasAuthorityAdmin
    @PutMapping("/products/{productId}")
    fun update(
        @PathVariable productId: ID,
        @Valid @RequestBody
        request: UpdateProductRequestDto
    ): ResponseEntity<ResultResponseDto> {
        val result: ID = service.update(
            productId = productId,
            request = request
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }

    @Operation(summary = "상품 삭제 API")
    @HasAuthorityAdmin
    @DeleteMapping("/products/{productId}")
    fun delete(@PathVariable productId: ID): ResponseEntity<ResultResponseDto> {
        val result: Boolean = service.delete(productId = productId)
        return ResponseEntity.ok(ResultResponseDto(result))
    }
}
