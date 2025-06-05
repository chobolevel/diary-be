package com.diary.api.controller.v1.admin.users

import com.diary.api.annotation.HasAuthorityAdmin
import com.diary.api.dto.common.ResultResponseDto
import com.diary.api.dto.users.points.AddUserPointRequestDto
import com.diary.api.dto.users.points.SubUserPointRequestDto
import com.diary.api.service.users.UserPointService
import com.diary.domain.type.ID
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "User Point(관리자 회원 포인트)", description = "관리자 회원 포인트 관리 API")
@RestController
@RequestMapping("/api/v1/admin")
class AdminUserPointController(
    private val service: UserPointService
) {

    @Operation(summary = "회원 포인트 지급 API")
    @HasAuthorityAdmin
    @PostMapping("/users/{id}/points/add")
    fun addPoint(
        @PathVariable("id") userId: ID,
        @Valid @RequestBody
        request: AddUserPointRequestDto
    ): ResponseEntity<ResultResponseDto> {
        val result: ID = service.addPoint(
            userId = userId,
            request = request
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }

    @Operation(summary = "회원 포인트 차감 API")
    @HasAuthorityAdmin
    @PostMapping("/users/{id}/points/sub")
    fun subPoint(
        @PathVariable("id") userId: ID,
        @Valid @RequestBody
        request: SubUserPointRequestDto
    ): ResponseEntity<ResultResponseDto> {
        val result: ID = service.subPoint(
            userId = userId,
            request = request
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }
}
