package com.daangn.api.auth

import com.daangn.api.dto.auth.JwtResponseDto
import com.daangn.api.dto.auth.LoginRequestDto
import com.daangn.api.dto.auth.LoginResponseDto
import com.daangn.api.dto.auth.ReissueRequestDto
import com.daangn.api.dto.auth.ReissueResponseDto
import com.daangn.api.properties.JwtProperties
import com.daangn.api.security.TokenProvider
import com.daangn.api.service.auth.AuthService
import com.daangn.api.service.auth.validator.AuthValidator
import com.daangn.api.users.DummyUser
import com.daangn.domain.entity.users.User
import com.daangn.domain.entity.users.UserRepositoryWrapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.anyString
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@ExtendWith(MockitoExtension::class)
@DisplayName("인증 서비스 단위 테스트")
class AuthServiceTest {

    private lateinit var dummyUser: User
    private lateinit var dummyUserToken: UsernamePasswordAuthenticationToken

    @Mock
    private lateinit var userRepositoryWrapper: UserRepositoryWrapper

    @Mock
    private lateinit var tokenProvider: TokenProvider

    @Mock
    private lateinit var passwordEncoder: BCryptPasswordEncoder

    @Mock
    private lateinit var redisTemplate: RedisTemplate<String, String>

    @Mock
    private lateinit var jwtProperties: JwtProperties

    @Mock
    private lateinit var validator: AuthValidator

    @InjectMocks
    private lateinit var service: AuthService

    @BeforeEach
    fun setup() {
        this.dummyUser = DummyUser.toEntity()
        this.dummyUserToken = DummyUser.toToken()
        `when`(redisTemplate.opsForHash<String, String>()).thenReturn(mock(RedisTemplate<String, String>().opsForHash<String, String>()::class.java))
        `when`(jwtProperties.refreshTokenCacheKey).thenReturn("refresh-token:v1")
    }

    @Test
    fun `로그인`() {
        // given
        val loginRequest: LoginRequestDto = DummyAuth.toLoginRequestDto()
        val jwtResponse = JwtResponseDto(
            tokenType = "Bearer",
            accessToken = "access-token",
            accessTokenExpiredAt = 0L,
            refreshToken = "refresh-token",
            refreshTokenExpiredAt = 0L,
        )
        doNothing().`when`(validator).validate(loginRequest)
        `when`(userRepositoryWrapper.findByEmail(loginRequest.email)).thenReturn(dummyUser)
        `when`(passwordEncoder.matches(anyString(), anyString())).thenReturn(true)
        `when`(tokenProvider.generateTokens(dummyUserToken)).thenReturn(jwtResponse)

        // when
        val result: LoginResponseDto = service.login(
            request = loginRequest
        )

        // then
        assertThat(result.tokenType).isEqualTo(jwtResponse.tokenType)
        assertThat(result.accessToken).isEqualTo(jwtResponse.accessToken)
        assertThat(result.accessTokenExpiredAt).isEqualTo(jwtResponse.accessTokenExpiredAt)
        assertThat(result.refreshToken).isEqualTo(jwtResponse.refreshToken)
        assertThat(result.refreshTokenExpiredAt).isEqualTo(jwtResponse.refreshTokenExpiredAt)
    }

    @Test
    fun `토큰 재발급`() {
        // given
        val reissueRequest: ReissueRequestDto = DummyAuth.toReissueRequestDto()
        val jwtResponse = JwtResponseDto(
            tokenType = "Bearer",
            accessToken = "access-token",
            accessTokenExpiredAt = 0L,
            refreshToken = "refresh-token",
            refreshTokenExpiredAt = 0L,
        )
        doNothing().`when`(tokenProvider).validateToken(reissueRequest.refreshToken)
        `when`(tokenProvider.getAuthentication(reissueRequest.refreshToken)).thenReturn(dummyUserToken)
        `when`(redisTemplate.opsForHash<String, String>().get(anyString(), anyString())).thenReturn(reissueRequest.refreshToken)
        `when`(tokenProvider.generateTokens(dummyUserToken)).thenReturn(jwtResponse)

        // when
        val result: ReissueResponseDto = service.reissue(
            refreshToken = "refresh-token"
        )

        // then
        assertThat(result.tokenType).isEqualTo(jwtResponse.tokenType)
        assertThat(result.accessToken).isEqualTo(jwtResponse.accessToken)
        assertThat(result.accessTokenExpiredAt).isEqualTo(jwtResponse.accessTokenExpiredAt)
        assertThat(result.refreshToken).isEqualTo(jwtResponse.refreshToken)
        assertThat(result.refreshTokenExpiredAt).isEqualTo(jwtResponse.refreshTokenExpiredAt)
    }

    @Test
    fun `로그아웃`() {
        // given
        val dummyUserId: String = dummyUser.id
        `when`(redisTemplate.opsForHash<String, String>()).thenReturn(mock(RedisTemplate<String, String>().opsForHash<String, String>()::class.java))
        `when`(redisTemplate.opsForHash<String, String>().delete(anyString(), anyString())).thenReturn(0L)

        // when
        val result: Boolean = service.logout(
            userId = dummyUserId
        )

        // then
        assertThat(result).isTrue()
    }
}
