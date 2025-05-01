package com.diary.api.controller.v1.admin.users

import com.diary.api.annotation.HasAuthorityAdmin
import com.diary.api.dto.common.ResultResponseDto
import com.diary.api.dto.users.CreateUserRequestDto
import com.diary.api.service.users.UserService
import com.diary.domain.type.ID
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "User(회원)", description = "관리자 회원 관리 API")
@RestController
@RequestMapping("/api/v1/admin")
class AdminUserController(
    private val service: UserService
) {

    @Operation(summary = "관리자 회원 등록 API")
    @HasAuthorityAdmin
    @PostMapping("/users")
    fun join(@Valid @RequestBody request: CreateUserRequestDto): ResponseEntity<ResultResponseDto> {
        val result: ID = service.join(request = request)
        return ResponseEntity.status(HttpStatus.CREATED).body(ResultResponseDto(result))
    }

}
