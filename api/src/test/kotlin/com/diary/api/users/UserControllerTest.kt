package com.diary.api.users

import com.diary.api.controller.v1.common.users.UserController
import com.diary.api.dto.common.ResultResponseDto
import com.diary.api.dto.users.ChangeUserPasswordRequestDto
import com.diary.api.dto.users.CreateUserRequestDto
import com.diary.api.dto.users.UpdateUserRequestDto
import com.diary.api.dto.users.UserResponseDto
import com.diary.api.service.users.UserService
import com.diary.domain.entity.users.User
import com.diary.domain.type.ID
import org.assertj.core.api.Assertions.assertThat
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
@DisplayName("회원 컨트롤러 단위 테스트")
class UserControllerTest {

    private val dummyUser: User = DummyUser.toEntity()
    private val dummyUserResponse: UserResponseDto = DummyUser.toResponseDto()
    private val dummyUserToken: UsernamePasswordAuthenticationToken = DummyUser.toToken()

    @Mock
    private lateinit var service: UserService

    @InjectMocks
    private lateinit var controller: UserController

    @Test
    fun `회원 가입`() {
        // given
        val dummyUserId: ID = dummyUser.id
        val request: CreateUserRequestDto = DummyUser.toCreateRequestDto()
        `when`(service.join(request = request)).thenReturn(dummyUserId)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.join(request = request)

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.CREATED)
        assertThat(result.body?.data).isEqualTo(dummyUserId)
    }

    @Test
    fun `회원 본인 정보 조회`() {
        // given
        val dummyUserId: ID = dummyUser.id
        `when`(service.getMe(id = dummyUserId)).thenReturn(dummyUserResponse)

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
    fun `회원 본인 정보 수정`() {
        // given
        val dummyUserId: ID = dummyUser.id
        val request: UpdateUserRequestDto = DummyUser.toUpdateRequestDto()
        `when`(
            service.update(
                id = dummyUserId,
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
    fun `회원 본인 비밀번호 변경`() {
        // given
        val dummyUserId: ID = dummyUser.id
        val request: ChangeUserPasswordRequestDto = DummyUser.toChangePasswordRequestDto()
        `when`(
            service.changePassword(
                id = dummyUserId,
                request = request
            )
        ).thenReturn(dummyUserId)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.changePassword(
            principal = dummyUserToken,
            request = request
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(dummyUserId)
    }

    @Test
    fun `회원 탈퇴`() {
        // given
        val dummyUserId: ID = dummyUser.id
        `when`(service.resign(id = dummyUserId)).thenReturn(true)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.resign(principal = dummyUserToken)

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(true)
    }
}
