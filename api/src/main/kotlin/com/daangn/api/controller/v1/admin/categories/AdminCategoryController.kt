package com.daangn.api.controller.v1.admin.categories

import com.daangn.api.annotation.HasAuthorityAdmin
import com.daangn.api.dto.categories.CreateCategoryRequestDto
import com.daangn.api.dto.categories.UpdateCategoryRequestDto
import com.daangn.api.dto.common.ResultResponseDto
import com.daangn.api.service.categories.CategoryService
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

@Tag(name = "Category(카테고리)", description = "관리자 카테고리 관리 API")
@RestController
@RequestMapping("/api/v1/admin")
class AdminCategoryController(
    private val service: CategoryService
) {

    @Operation(summary = "카테고리 등록 API")
    @HasAuthorityAdmin
    @PostMapping("/categories")
    fun create(
        @Valid @RequestBody
        request: CreateCategoryRequestDto
    ): ResponseEntity<ResultResponseDto> {
        val result = service.create(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(ResultResponseDto(result))
    }

    @Operation(summary = "카테고리 수정 API")
    @HasAuthorityAdmin
    @PutMapping("/categories/{id}")
    fun update(
        @PathVariable("id") categoryId: String,
        @Valid @RequestBody
        request: UpdateCategoryRequestDto
    ): ResponseEntity<ResultResponseDto> {
        val result = service.update(
            categoryId = categoryId,
            request = request
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }

    @Operation(summary = "카테고리 삭제 API")
    @HasAuthorityAdmin
    @DeleteMapping("/categories/{id}")
    fun delete(@PathVariable("id") categoryId: String): ResponseEntity<ResultResponseDto> {
        val result = service.delete(categoryId)
        return ResponseEntity.ok(ResultResponseDto(result))
    }
}
