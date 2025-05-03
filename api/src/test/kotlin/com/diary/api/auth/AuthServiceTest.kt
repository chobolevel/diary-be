package com.diary.api.auth

import com.diary.api.dto.auth.LoginRequestDto
import com.diary.api.dto.auth.LoginResponseDto
import com.diary.api.dto.auth.ReissueResponseDto
import com.diary.api.properties.JwtProperties
import com.diary.api.security.TokenProvider
import com.diary.api.service.auth.AuthService
import com.diary.api.users.DummyUser
import com.diary.domain.entity.users.User
import com.diary.domain.entity.users.UserRepositoryWrapper
import com.diary.domain.type.ID
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.redis.core.HashOperations
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@ExtendWith(MockitoExtension::class)
@DisplayName("인증 서비스 로직 단위 테스트")
class AuthServiceTest {

    private val dummyUser: User = DummyUser.toEntity()

    private val dummyAccessToken: String = "access-token"
    private val dummyRefreshToken: String = "refresh-token"

    @Mock
    private lateinit var userRepositoryWrapper: UserRepositoryWrapper

    @Mock
    private lateinit var passwordEncoder: BCryptPasswordEncoder

    @Mock
    private lateinit var tokenProvider: TokenProvider

    @Mock
    private lateinit var opsForHash: HashOperations<String, String, String>

    @Mock
    private lateinit var jwtProperties: JwtProperties

    @InjectMocks
    private lateinit var service: AuthService

    @BeforeEach
    fun setup() {
        `when`(jwtProperties.refreshTokenCacheKey).thenReturn("refresh_token:user:v1")
    }

    @Test
    fun `로그인`() {
        // given
        val request: LoginRequestDto = DummyAuth.toLoginRequestDto()
        val dummyToken = UsernamePasswordAuthenticationToken(
            dummyUser.id,
            dummyUser.password,
            AuthorityUtils.createAuthorityList(dummyUser.role.name)
        )
        `when`(userRepositoryWrapper.findByUsername(username = request.username)).thenReturn(dummyUser)
        `when`(passwordEncoder.matches(request.password, dummyUser.password)).thenReturn(true)
        `when`(tokenProvider.issueTokenPair(authentication = dummyToken)).thenReturn(Pair(dummyAccessToken, dummyRefreshToken))
        doNothing().`when`(opsForHash).put(anyString(), anyString(), anyString())

        // when
        val result: LoginResponseDto = service.login(request = request)

        // then
        assertThat(result.accessToken).isEqualTo(dummyAccessToken)
        assertThat(result.refreshToken).isEqualTo(dummyRefreshToken)
    }

    @Test
    fun `토큰 재발급`() {
        // given
        val dummyToken = UsernamePasswordAuthenticationToken(
            dummyUser.id,
            dummyUser.password,
            AuthorityUtils.createAuthorityList(dummyUser.role.name)
        )
        val dummyUserId: ID = dummyUser.id
        `when`(tokenProvider.validateToken(token = dummyRefreshToken)).thenReturn(true)
        `when`(tokenProvider.getAuthentication(dummyRefreshToken)).thenReturn(dummyToken)
        `when`(opsForHash.get(jwtProperties.refreshTokenCacheKey, dummyRefreshToken)).thenReturn(dummyUserId)
        `when`(tokenProvider.issueTokenPair(authentication = dummyToken)).thenReturn(Pair(dummyAccessToken, dummyRefreshToken))

        // when
        val result: ReissueResponseDto = service.reissue(refreshToken = dummyRefreshToken)

        // then
        assertThat(result.accessToken).isEqualTo(dummyAccessToken)
    }

    @Test
    fun `로그아웃`() {
        // given
        `when`(tokenProvider.validateToken(token = dummyRefreshToken)).thenReturn(true)
        `when`(opsForHash.delete(jwtProperties.refreshTokenCacheKey, dummyRefreshToken)).thenReturn(0L)

        // when
        val result: Boolean = service.logout(refreshToken = dummyRefreshToken)

        // then
        assertThat(result).isTrue()
    }
}
