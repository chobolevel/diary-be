package com.diary.api.controller.v1.admin.products

import com.diary.api.annotation.HasAuthorityAdmin
import com.diary.api.dto.common.ResultResponseDto
import com.diary.api.dto.products.options.CreateProductOptionRequestDto
import com.diary.api.dto.products.options.UpdateProductOptionRequestDto
import com.diary.api.service.products.ProductOptionService
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

@Tag(name = "Product Option(상품 옵션)", description = "관리자 상품 옵션 관리 API")
@RestController
@RequestMapping("/api/v1/admin")
class AdminProductOptionController(
    private val service: ProductOptionService
) {

    @Operation(summary = "상품 옵션 등록 API")
    @HasAuthorityAdmin
    @PostMapping("/products/{productId}/options")
    fun create(
        @PathVariable productId: ID,
        @Valid @RequestBody
        request: CreateProductOptionRequestDto
    ): ResponseEntity<ResultResponseDto> {
        val result: ID = service.create(
            productId = productId,
            request = request
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(ResultResponseDto(result))
    }

    @Operation(summary = "상품 옵션 정보 수정 API")
    @HasAuthorityAdmin
    @PutMapping("/products/{productId}/options/{productOptionId}")
    fun update(
        @PathVariable productId: ID,
        @PathVariable productOptionId: ID,
        @Valid @RequestBody
        request: UpdateProductOptionRequestDto
    ): ResponseEntity<ResultResponseDto> {
        val result: ID = service.update(
            productId = productId,
            productOptionId = productOptionId,
            request = request
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }

    @Operation(summary = "상품 옵션 삭제 API")
    @HasAuthorityAdmin
    @DeleteMapping("/products/{productId}/options/{productOptionId}")
    fun delete(
        @PathVariable productId: ID,
        @PathVariable productOptionId: ID,
    ): ResponseEntity<ResultResponseDto> {
        val result: Boolean = service.delete(
            productId = productId,
            productOptionId = productOptionId
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }
}
