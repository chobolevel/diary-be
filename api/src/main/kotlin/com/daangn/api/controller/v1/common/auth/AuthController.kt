package com.daangn.api.controller.v1.common.auth

import com.daangn.api.annotation.HasAuthorityUser
import com.daangn.api.dto.auth.LoginRequestDto
import com.daangn.api.dto.auth.LoginResponseDto
import com.daangn.api.dto.auth.ReissueResponseDto
import com.daangn.api.dto.common.ResultResponseDto
import com.daangn.api.properties.JwtProperties
import com.daangn.api.service.auth.AuthService
import com.daangn.api.util.getCookie
import com.daangn.api.util.getUserId
import com.daangn.domain.exception.ErrorCode
import com.daangn.domain.exception.PolicyException
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
import java.security.Principal

@Tag(name = "Auth(인증)", description = "인증 관리 API")
@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val service: AuthService,
    private val jwtProperties: JwtProperties,
    private val serverProperties: ServerProperties
) {

    @Operation(summary = "로그인 API")
    @PostMapping("/login")
    fun login(
        res: HttpServletResponse,
        @Valid @RequestBody
        request: LoginRequestDto
    ): ResponseEntity<ResultResponseDto> {
        val result: LoginResponseDto = service.login(request = request)
        val accessTokenCookie = generateCookie(
            key = jwtProperties.accessTokenKey,
            value = result.accessToken
        )
        val refreshTokenCookie = generateCookie(
            key = jwtProperties.refreshTokenKey,
            value = result.refreshToken
        )
        res.addCookie(accessTokenCookie)
        res.addCookie(refreshTokenCookie)
        return ResponseEntity.ok(ResultResponseDto(true))
    }

    @Operation(summary = "토큰 재발급 API")
    @PostMapping("/reissue")
    fun reissue(
        req: HttpServletRequest,
        res: HttpServletResponse
    ): ResponseEntity<ResultResponseDto> {
        val refreshToken = req.getCookie(jwtProperties.refreshTokenKey) ?: throw PolicyException(
            errorCode = ErrorCode.REFRESH_TOKEN_NOT_EXISTS,
            message = ErrorCode.REFRESH_TOKEN_NOT_EXISTS.message
        )
        val result: ReissueResponseDto = service.reissue(refreshToken = refreshToken)
        val accessTokenCookie = generateCookie(
            key = jwtProperties.accessTokenKey,
            value = result.accessToken
        )
        res.addCookie(accessTokenCookie)
        return ResponseEntity.ok(ResultResponseDto(true))
    }

    @Operation(summary = "로그아웃 API")
    @HasAuthorityUser
    @PostMapping("/logout")
    fun logout(res: HttpServletResponse, principal: Principal): ResponseEntity<ResultResponseDto> {
        val result = service.logout(
            userId = principal.getUserId()
        )
        val accessTokenCookie = generateCookie(
            key = jwtProperties.accessTokenKey,
            value = "",
            maxAge = 0
        )
        val refreshTokenCookie = generateCookie(
            key = jwtProperties.refreshTokenKey,
            value = "",
            maxAge = 0
        )
        res.addCookie(accessTokenCookie)
        res.addCookie(refreshTokenCookie)
        return ResponseEntity.ok(ResultResponseDto(result))
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
