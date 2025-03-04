package com.daangn.api.users

import com.daangn.api.controller.v1.common.users.UserController
import com.daangn.api.dto.common.ResultResponseDto
import com.daangn.api.dto.users.CreateUserRequestDto
import com.daangn.api.dto.users.UpdateUserRequestDto
import com.daangn.api.dto.users.UserResponseDto
import com.daangn.api.service.users.UserService
import com.daangn.api.service.users.query.UserQueryCreator
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
import org.springframework.security.core.authority.AuthorityUtils

@ExtendWith(MockitoExtension::class)
@DisplayName("회원 컨트롤러 단위 테스트")
class UserControllerTest {

    private lateinit var dummyUserToken: UsernamePasswordAuthenticationToken
    private lateinit var dummyUser: User
    private lateinit var dummyUserResponse: UserResponseDto

    @Mock
    private lateinit var service: UserService

    @Mock
    private lateinit var queryCreator: UserQueryCreator

    @InjectMocks
    private lateinit var controller: UserController

    @BeforeEach
    fun setup() {
        this.dummyUser = DummyUser.toEntity()
        this.dummyUserResponse = DummyUser.toResponseDto()
        this.dummyUserToken = UsernamePasswordAuthenticationToken(
            dummyUser.id,
            dummyUser.password,
            AuthorityUtils.createAuthorityList(dummyUser.role.name)
        )
    }

    @Test
    fun `회원 가입`() {
        // given
        val dummyUserId: String = dummyUser.id
        val request: CreateUserRequestDto = DummyUser.toCreateRequestDto()
        `when`(
            service.create(
                request = request
            )
        ).thenReturn(dummyUserId)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.create(
            request = request
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.CREATED)
        assertThat(result.body?.data).isEqualTo(dummyUserId)
    }

    @Test
    fun `회원 본인 정보 조회`() {
        // given
        val dummyUserId: String = dummyUser.id
        `when`(
            service.getUser(
                userId = dummyUserId
            )
        ).thenReturn(dummyUserResponse)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.getMe(
            principal = dummyUserToken
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(dummyUserResponse)
    }

    @Test
    fun `회원 정보 수정`() {
        // given
        val dummyUserId: String = dummyUser.id
        val request: UpdateUserRequestDto = DummyUser.toUpdateRequestDto()
        `when`(
            service.update(
                userId = dummyUserId,
                request = request
            )
        ).thenReturn(dummyUserId)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.update(
            principal = dummyUserToken,
            request = request
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(dummyUserId)
    }

    @Test
    fun `회원 탍퇴`() {
        // given
        val dummyUserId: String = dummyUser.id
        `when`(
            service.resign(
                userId = dummyUserId
            )
        ).thenReturn(true)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.resign(
            principal = dummyUserToken
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(true)
    }
}
