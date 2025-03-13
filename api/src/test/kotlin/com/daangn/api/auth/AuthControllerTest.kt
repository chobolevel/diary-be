package com.daangn.api.auth

import com.daangn.api.controller.v1.common.auth.AuthController
import com.daangn.api.dto.auth.LoginRequestDto
import com.daangn.api.dto.auth.LoginResponseDto
import com.daangn.api.dto.auth.ReissueResponseDto
import com.daangn.api.dto.common.ResultResponseDto
import com.daangn.api.properties.JwtProperties
import com.daangn.api.service.auth.AuthService
import com.daangn.api.users.DummyUser
import com.daangn.domain.entity.users.User
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.any
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.autoconfigure.web.ServerProperties
import org.springframework.boot.web.server.Cookie
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import java.time.Duration

@ExtendWith(MockitoExtension::class)
@DisplayName("인증 컨트롤러 단위 테스트")
class AuthControllerTest {

    private lateinit var dummyUser: User
    private lateinit var dummyUserToken: UsernamePasswordAuthenticationToken

    private lateinit var dummyLoginResponse: LoginResponseDto
    private lateinit var dummyReissueResponse: ReissueResponseDto

    private lateinit var dummyHttpServletRequest: HttpServletRequest
    private lateinit var dummyHttpServletResponse: HttpServletResponse

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
        dummyUser = DummyUser.toEntity()
        dummyUserToken = DummyUser.toToken()
        dummyLoginResponse = DummyAuth.toLoginResponseDto()
        dummyReissueResponse = DummyAuth.toReissueResponseDto()
        dummyHttpServletRequest = mock(HttpServletRequest::class.java)
        dummyHttpServletResponse = mock(HttpServletResponse::class.java)
        doNothing().`when`(dummyHttpServletResponse).addCookie(any())
        `when`(jwtProperties.accessTokenKey).thenReturn("_dat")
        `when`(jwtProperties.refreshTokenKey).thenReturn("_drt")
        `when`(serverProperties.reactive).thenReturn(mock(ServerProperties.Reactive::class.java))
        `when`(serverProperties.reactive.session).thenReturn(mock(ServerProperties.Reactive.Session::class.java))
        `when`(serverProperties.reactive.session.cookie).thenReturn(mock(Cookie::class.java))
        `when`(serverProperties.reactive.session.cookie.path).thenReturn("/")

        `when`(serverProperties.reactive.session.cookie.domain).thenReturn("localhost")
        `when`(serverProperties.reactive.session.cookie.secure).thenReturn(true)
        `when`(serverProperties.reactive.session.cookie.httpOnly).thenReturn(true)
        `when`(serverProperties.reactive.session.cookie.sameSite).thenReturn(Cookie.SameSite.NONE)
    }

    @Test
    fun `로그인`() {
        // given
        val request: LoginRequestDto = DummyAuth.toLoginRequestDto()
        `when`(service.login(request)).thenReturn(dummyLoginResponse)
        `when`(serverProperties.reactive.session.cookie.maxAge).thenReturn(Duration.ofMillis(31536000))

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
        val refreshToken = "refresh-token"
        `when`(service.reissue(refreshToken = refreshToken)).thenReturn(dummyReissueResponse)
        `when`(dummyHttpServletRequest.cookies).thenReturn(
            arrayOf(
                jakarta.servlet.http.Cookie("_drt", refreshToken),
            )
        )
        `when`(serverProperties.reactive.session.cookie.maxAge).thenReturn(Duration.ofMillis(31536000))

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.reissue(
            req = dummyHttpServletRequest,
            res = dummyHttpServletResponse,
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(true)
    }

    @Test
    fun `로그아웃`() {
        // given
        val dummyUserId: String = dummyUser.id
        `when`(
            service.logout(
                userId = dummyUserId
            )
        ).thenReturn(true)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.logout(
            res = dummyHttpServletResponse,
            principal = dummyUserToken
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(true)
    }
}
