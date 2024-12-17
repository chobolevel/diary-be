package com.scrimmers.api.controller.v1.auth

import com.scrimmers.api.dto.auth.LoginRequestDto
import com.scrimmers.api.dto.common.ResultResponse
import com.scrimmers.api.service.auth.AuthService
import com.scrimmers.domain.exception.ErrorCode
import com.scrimmers.domain.exception.PolicyException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.boot.autoconfigure.web.ServerProperties
import org.springframework.http.HttpStatus
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
    private val serverProperties: ServerProperties
) {

    @Operation(summary = "로그인 API")
    @PostMapping("/auth/login")
    fun generalLogin(
        res: HttpServletResponse,
        @Valid @RequestBody
        request: LoginRequestDto
    ): ResponseEntity<ResultResponse> {
        val result = service.login(request)
        val accessTokenCookie = generateCookie("_sat", result.accessToken)
        val refreshTokenCookie = generateCookie("_srt", result.refreshToken)
        res.addCookie(accessTokenCookie)
        res.addCookie(refreshTokenCookie)
        return ResponseEntity.ok(ResultResponse(true))
    }

    @Operation(summary = "로그아웃 API")
    @PostMapping("/auth/logout")
    fun logout(res: HttpServletResponse): ResponseEntity<ResultResponse> {
        val accessTokenCookie = generateCookie("_sat", "", 0)
        val refreshTokenCookie = generateCookie("_srt", "", 0)
        res.addCookie(accessTokenCookie)
        res.addCookie(refreshTokenCookie)
        return ResponseEntity.ok(ResultResponse(true))
    }

    @Operation(summary = "회원 토큰 재발급 API")
    @PostMapping("/auth/reissue")
    fun reissueToken(
        req: HttpServletRequest,
        res: HttpServletResponse,
    ): ResponseEntity<ResultResponse> {
        val refreshToken = try {
            req.cookies.find { it.name == "_srt" }!!.value
        } catch (e: Exception) {
            throw PolicyException(
                errorCode = ErrorCode.INVALID_TOKEN,
                status = HttpStatus.UNAUTHORIZED,
                message = "토큰이 만료되었습니다. 재로그인 해주세요."
            )
        }
        val result = service.reissueToken(refreshToken)
        val accessTokenCookie = generateCookie("_sat", result.accessToken)
        res.addCookie(accessTokenCookie)
        return ResponseEntity.ok(ResultResponse(true))
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
