package com.diary.api.users

import com.diary.api.controller.v1.admin.users.AdminUserController
import com.diary.api.dto.common.ResultResponseDto
import com.diary.api.dto.users.CreateUserRequestDto
import com.diary.api.dto.users.UserResponseDto
import com.diary.api.service.users.UserService
import com.diary.domain.entity.users.User
import com.diary.domain.type.ID
import org.assertj.core.api.Assertions
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

@ExtendWith(MockitoExtension::class)
@DisplayName("관리자 회원 API 단위 테스트")
class AdminUserControllerTest {

    private val dummyUser: User = DummyUser.toEntity()
    private val dummyUserResponse: UserResponseDto = DummyUser.toResponseDto()

    @Mock
    private lateinit var service: UserService

    @InjectMocks
    private lateinit var controller: AdminUserController

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


}
