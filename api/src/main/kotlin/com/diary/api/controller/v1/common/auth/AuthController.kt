package com.diary.api.controller.v1.common.auth

import com.diary.api.dto.auth.LoginRequestDto
import com.diary.api.dto.auth.LoginResponseDto
import com.diary.api.dto.auth.ReissueResponseDto
import com.diary.api.dto.common.ResultResponseDto
import com.diary.api.properties.JwtProperties
import com.diary.api.service.auth.AuthService
import com.diary.api.util.getCookie
import com.diary.domain.exception.ErrorCode
import com.diary.domain.exception.PolicyException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.boot.autoconfigure.web.ServerProperties
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Auth(인증)", description = "인증 관리 API")
@RestController
@RequestMapping("/api/v1")
class AuthController(
    private val service: AuthService,
    private val jwtProperties: JwtProperties,
    private val serverProperties: ServerProperties
) {

    @Operation(summary = "로그인 API")
    @PostMapping("/auth/login")
    fun login(
        res: HttpServletResponse,
        @Valid @RequestBody
        request: LoginRequestDto
    ): ResponseEntity<ResultResponseDto> {
        val result: LoginResponseDto = service.login(request = request)
        res.addCookie(
            generateCookie(
                key = jwtProperties.accessTokenKey,
                value = result.accessToken
            )
        )
        res.addCookie(
            generateCookie(
                key = jwtProperties.refreshTokenKey,
                value = result.refreshToken
            )
        )
        return ResponseEntity.ok(ResultResponseDto(true))
    }

    @Operation(summary = "토큰 재발급 API")
    @PostMapping("/auth/reissue")
    fun reissue(
        req: HttpServletRequest,
        res: HttpServletResponse
    ): ResponseEntity<ResultResponseDto> {
        val refreshToken: String = req.getCookie(key = jwtProperties.refreshTokenKey) ?: throw PolicyException(
            errorCode = ErrorCode.REFRESH_TOKEN_NOT_EXISTS,
            message = ErrorCode.REFRESH_TOKEN_NOT_EXISTS.message
        )
        val result: ReissueResponseDto = service.reissue(refreshToken = refreshToken)
        res.addCookie(
            generateCookie(
                key = jwtProperties.accessTokenKey,
                value = result.accessToken
            )
        )
        return ResponseEntity.ok(ResultResponseDto(true))
    }

    @Operation(summary = "로그아웃 API")
    @PostMapping("/auth/logout")
    fun logout(
        req: HttpServletRequest,
        res: HttpServletResponse
    ): ResponseEntity<ResultResponseDto> {
        val refreshToken: String? = req.getCookie(key = jwtProperties.refreshTokenKey)
        if (!refreshToken.isNullOrEmpty()) {
            service.logout(refreshToken = refreshToken)
        }
        res.addCookie(
            generateCookie(
                key = jwtProperties.accessTokenKey,
                value = "",
                maxAge = 0
            )
        )
        res.addCookie(
            generateCookie(
                key = jwtProperties.refreshTokenKey,
                value = "",
                maxAge = 0
            )
        )
        return ResponseEntity.ok(ResultResponseDto(true))
    }

    private fun generateCookie(
        key: String,
        value: String,
        maxAge: Int = serverProperties.reactive.session.cookie.maxAge.toSeconds().toInt()
    ): Cookie {
        return Cookie(key, value).also {
            it.path = serverProperties.reactive.session.cookie.path
            it.maxAge = maxAge
            it.domain = serverProperties.reactive.session.cookie.domain
            it.secure = serverProperties.reactive.session.cookie.secure
            it.isHttpOnly = serverProperties.reactive.session.cookie.httpOnly
            it.setAttribute("SameSite", serverProperties.reactive.session.cookie.sameSite.attributeValue())
        }
    }
}
