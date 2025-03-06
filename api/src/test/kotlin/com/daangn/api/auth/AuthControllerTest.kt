package com.daangn.api.auth

import com.daangn.api.controller.v1.common.auth.AuthController
import com.daangn.api.dto.auth.LoginRequestDto
import com.daangn.api.dto.auth.LoginResponseDto
import com.daangn.api.dto.auth.ReissueRequestDto
import com.daangn.api.dto.auth.ReissueResponseDto
import com.daangn.api.dto.common.ResultResponseDto
import com.daangn.api.service.auth.AuthService
import com.daangn.api.users.DummyUser
import com.daangn.domain.entity.users.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

@ExtendWith(MockitoExtension::class)
@DisplayName("인증 컨트롤러 단위 테스트")
class AuthControllerTest {

    private lateinit var dummyUser: User
    private lateinit var dummyUserToken: UsernamePasswordAuthenticationToken

    private lateinit var dummyLoginResponse: LoginResponseDto
    private lateinit var dummyReissueResponse: ReissueResponseDto

    @Mock
    private lateinit var service: AuthService

    @InjectMocks
    private lateinit var controller: AuthController

    @BeforeEach
    fun setup() {
        dummyUser = DummyUser.toEntity()
        dummyUserToken = DummyUser.toToken()
        dummyLoginResponse = DummyAuth.toLoginResponseDto()
        dummyReissueResponse = DummyAuth.toReissueResponseDto()
    }

    @Test
    fun `로그인`() {
        // given
        val request: LoginRequestDto = DummyAuth.toLoginRequestDto()
        `when`(service.login(request)).thenReturn(dummyLoginResponse)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.login(
            request = request
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(dummyLoginResponse)
    }

    @Test
    fun `토큰 재발급`() {
        // given
        val request: ReissueRequestDto = DummyAuth.toReissueRequestDto()
        `when`(service.reissue(request = request)).thenReturn(dummyReissueResponse)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.reissue(
            request = request
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(dummyReissueResponse)
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
            principal = dummyUserToken
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(true)
    }
}
