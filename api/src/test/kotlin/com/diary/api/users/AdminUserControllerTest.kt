package com.diary.api.users

import com.diary.api.controller.v1.admin.users.AdminUserController
import com.diary.api.dto.common.PaginationResponseDto
import com.diary.api.dto.common.ResultResponseDto
import com.diary.api.dto.users.ChangeUserPasswordRequestDto
import com.diary.api.dto.users.CreateUserRequestDto
import com.diary.api.dto.users.UpdateUserRequestDto
import com.diary.api.dto.users.UserResponseDto
import com.diary.api.service.users.UserService
import com.diary.domain.dto.Pagination
import com.diary.domain.entity.users.User
import com.diary.domain.entity.users.UserOrderType
import com.diary.domain.entity.users.UserQueryFilter
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

    @Test
    fun `회원 목록 조회`() {
        // given
        val queryFilter = UserQueryFilter(
            username = null,
            signUpType = null,
            nickname = null,
            role = null,
            resigned = null
        )
        val pagination = Pagination(
            page = 1,
            size = 20
        )
        val orderTypes: List<UserOrderType> = listOfNotNull()
        val dummyUserResponses: List<UserResponseDto> = listOf(dummyUserResponse)
        val paginationResponse = PaginationResponseDto(
            page = pagination.page,
            size = pagination.size,
            data = dummyUserResponses,
            totalCount = dummyUserResponses.size.toLong()
        )
        `when`(
            service.getUsers(
                queryFilter = queryFilter,
                pagination = pagination,
                orderTypes = orderTypes
            )
        ).thenReturn(paginationResponse)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.getUsers(
            username = null,
            signUpType = null,
            nickname = null,
            role = null,
            resigned = null,
            page = null,
            size = null,
            orderTypes = null
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(paginationResponse)
    }

    @Test
    fun `관리자 회원 단건 조회`() {
        // given
        val dummyUserId: ID = dummyUser.id
        `when`(service.getUser(id = dummyUserId)).thenReturn(dummyUserResponse)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.getUser(userId = dummyUserId)

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(dummyUserResponse)
    }

    @Test
    fun `관리자 회원 정보 수정`() {
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
            userId = dummyUserId,
            request = request
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(dummyUserId)
    }

    @Test
    fun `괸리자 회원 비밀번호 변경`() {
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
            userId = dummyUserId,
            request = request
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(dummyUserId)
    }

    @Test
    fun `관리자 회원 탈퇴 처리`() {
        // given
        val dummyUserId: ID = dummyUser.id
        `when`(service.resign(id = dummyUserId)).thenReturn(true)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.resign(userId = dummyUserId)

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(true)
    }
}
