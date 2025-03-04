package com.daangn.api.controller.v1.common.users

import com.daangn.api.annotation.HasAuthorityUser
import com.daangn.api.dto.common.ResultResponseDto
import com.daangn.api.dto.users.CreateUserRequestDto
import com.daangn.api.dto.users.UpdateUserRequestDto
import com.daangn.api.getUserId
import com.daangn.api.service.users.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@Tag(name = "Users(회원)", description = "회원 관리 API")
@RestController
@RequestMapping("/api/v1")
class UserController(
    private val service: UserService,
) {

    @Operation(summary = "회원 가입 API")
    @PostMapping("/users")
    fun create(
        @Valid @RequestBody
        request: CreateUserRequestDto
    ): ResponseEntity<ResultResponseDto> {
        val result = service.create(
            request = request
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(ResultResponseDto(result))
    }

    @Operation(summary = "회원 본인 정보 조회 API")
    @HasAuthorityUser
    @GetMapping("/user/me")
    fun getMe(principal: Principal): ResponseEntity<ResultResponseDto> {
        val result = service.getUser(
            userId = principal.getUserId()
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }

    @Operation(summary = "회원 본인 정보 수정 API")
    @HasAuthorityUser
    @PutMapping("/user/me")
    fun update(
        principal: Principal,
        @Valid @RequestBody
        request: UpdateUserRequestDto
    ): ResponseEntity<ResultResponseDto> {
        val result = service.update(
            userId = principal.getUserId(),
            request = request
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }

    @Operation(summary = "회원 탈퇴 API")
    @HasAuthorityUser
    @DeleteMapping("/user/resign")
    fun resign(principal: Principal): ResponseEntity<ResultResponseDto> {
        val result = service.resign(
            userId = principal.getUserId()
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }
}
