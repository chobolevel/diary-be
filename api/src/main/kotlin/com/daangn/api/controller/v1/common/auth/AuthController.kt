package com.daangn.api.controller.v1.common.auth

import com.daangn.api.annotation.HasAuthorityUser
import com.daangn.api.dto.auth.LoginRequestDto
import com.daangn.api.dto.auth.ReissueRequestDto
import com.daangn.api.dto.common.ResultResponseDto
import com.daangn.api.service.auth.AuthService
import com.daangn.api.util.getUserId
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@Tag(name = "Auth(인증)", description = "인증 관리 API")
@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val service: AuthService
) {

    @Operation(summary = "로그인 API")
    @PostMapping("/login")
    fun login(
        @Valid @RequestBody
        request: LoginRequestDto
    ): ResponseEntity<ResultResponseDto> {
        val result = service.login(request = request)
        return ResponseEntity.ok(ResultResponseDto(result))
    }

    @Operation(summary = "토큰 재발급 API")
    @PostMapping("/reissue")
    fun reissue(
        @Valid @RequestBody
        request: ReissueRequestDto
    ): ResponseEntity<ResultResponseDto> {
        val result = service.reissue(request = request)
        return ResponseEntity.ok(ResultResponseDto(result))
    }

    @Operation(summary = "로그아웃 API")
    @HasAuthorityUser
    @PostMapping("/logout")
    fun logout(principal: Principal): ResponseEntity<ResultResponseDto> {
        val result = service.logout(
            userId = principal.getUserId()
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }
}
