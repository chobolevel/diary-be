package com.diary.api.auth

import com.diary.api.controller.v1.common.auth.AuthController
import com.diary.api.dto.auth.LoginRequestDto
import com.diary.api.dto.auth.LoginResponseDto
import com.diary.api.dto.auth.ReissueResponseDto
import com.diary.api.dto.common.ResultResponseDto
import com.diary.api.properties.JwtProperties
import com.diary.api.service.auth.AuthService
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.autoconfigure.web.ServerProperties
import org.springframework.boot.web.servlet.server.Session
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@ExtendWith(MockitoExtension::class)
@DisplayName("인증 컨트롤러 단위 테스트")
class AuthControllerTest {

    private val dummyRefreshToken = "refresh-token"

    private val dummyHttpServletRequest = mock(HttpServletRequest::class.java)
    private val dummyHttpServletResponse = mock(HttpServletResponse::class.java)

    @Mock
    private lateinit var service: AuthService

    @Mock
    private lateinit var jwtProperties: JwtProperties

    @Mock
    private lateinit var serverProperties: ServerProperties

    @InjectMocks
    private lateinit var controller: AuthController

    @BeforeEach
    fun setup() {
        `when`(jwtProperties.accessTokenKey).thenReturn("_cdat")
        `when`(jwtProperties.refreshTokenKey).thenReturn("_cdrt")
        `when`(serverProperties.reactive).thenReturn(mock(ServerProperties.Reactive::class.java))
        `when`(serverProperties.reactive.session).thenReturn(mock(ServerProperties.Reactive.Session::class.java))
        `when`(serverProperties.reactive.session.cookie).thenReturn(mock(Session.Cookie::class.java))
        `when`(serverProperties.reactive.session.cookie.path).thenReturn("/")
        `when`(serverProperties.reactive.session.cookie.domain).thenReturn("localhost")
        `when`(serverProperties.reactive.session.cookie.secure).thenReturn(true)
        `when`(serverProperties.reactive.session.cookie.httpOnly).thenReturn(true)
        `when`(serverProperties.reactive.session.cookie.sameSite).thenReturn(org.springframework.boot.web.server.Cookie.SameSite.NONE)
        `when`(dummyHttpServletRequest.cookies).thenReturn(
            arrayOf(
                Cookie(
                    "_cdrt",
                    dummyRefreshToken
                )
            )
        )
        doNothing().`when`(dummyHttpServletResponse).addCookie(any())
    }

    @Test
    fun `로그인`() {
        // given
        val request: LoginRequestDto = DummyAuth.toLoginRequestDto()
        val response: LoginResponseDto = DummyAuth.toLoginResponseDto()
        `when`(service.login(request = request)).thenReturn(response)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.login(
            res = dummyHttpServletResponse,
            request = request
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(true)
    }

    @Test
    fun `토큰 재발급`() {
        // given
        val response: ReissueResponseDto = DummyAuth.toReissueResponseDto()
        `when`(service.reissue(refreshToken = dummyRefreshToken)).thenReturn(response)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.reissue(
            req = dummyHttpServletRequest,
            res = dummyHttpServletResponse
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(true)
    }

    @Test
    fun `로그아웃`() {
        // given
        `when`(service.logout(refreshToken = dummyRefreshToken)).thenReturn(true)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.logout(
            req = dummyHttpServletRequest,
            res = dummyHttpServletResponse
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(true)
    }
}
