package com.diary.api.controller.v1.common.users

import com.diary.api.annotation.HasAuthorityUser
import com.diary.api.dto.common.ResultResponseDto
import com.diary.api.dto.users.ChangeUserPasswordRequestDto
import com.diary.api.dto.users.CreateUserRequestDto
import com.diary.api.dto.users.UpdateUserRequestDto
import com.diary.api.dto.users.UserResponseDto
import com.diary.api.service.users.UserService
import com.diary.api.util.getUserId
import com.diary.domain.type.ID
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

@Tag(name = "User(회원)", description = "회원 관리 API")
@RestController
@RequestMapping("/api/v1")
class UserController(
    private val service: UserService
) {

    @Operation(summary = "회원 가입 API")
    @PostMapping("/users")
    fun join(
        @Valid @RequestBody
        request: CreateUserRequestDto
    ): ResponseEntity<ResultResponseDto> {
        val result: ID = service.join(request = request)
        return ResponseEntity.status(HttpStatus.CREATED).body(ResultResponseDto(result))
    }

    @Operation(summary = "회원(본인) 정보 조회 API")
    @HasAuthorityUser
    @GetMapping("/user/me")
    fun getMe(principal: Principal): ResponseEntity<ResultResponseDto> {
        val result: UserResponseDto = service.getMe(id = principal.getUserId())
        return ResponseEntity.ok(ResultResponseDto(result))
    }

    @Operation(summary = "회원(본인) 정보 수정 API")
    @HasAuthorityUser
    @PutMapping("/user/me")
    fun update(
        principal: Principal,
        @Valid @RequestBody
        request: UpdateUserRequestDto
    ): ResponseEntity<ResultResponseDto> {
        val result: ID = service.update(
            id = principal.getUserId(),
            request = request
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }

    @Operation(summary = "회원(본인) 비밀번호 변경 API")
    @HasAuthorityUser
    @PostMapping("/user/change-password")
    fun changePassword(
        principal: Principal,
        @Valid @RequestBody
        request: ChangeUserPasswordRequestDto
    ): ResponseEntity<ResultResponseDto> {
        val result: ID = service.changePassword(
            id = principal.getUserId(),
            request = request
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }

    @Operation(summary = "회원(본인) 탈퇴 API")
    @HasAuthorityUser
    @DeleteMapping("/user/resign")
    fun resign(principal: Principal): ResponseEntity<ResultResponseDto> {
        val result: Boolean = service.resign(id = principal.getUserId())
        return ResponseEntity.ok(ResultResponseDto(result))
    }
}
