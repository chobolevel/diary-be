package com.daangn.api.users

import com.daangn.api.controller.v1.admin.users.AdminUserController
import com.daangn.api.dto.common.PaginationResponseDto
import com.daangn.api.dto.common.ResultResponseDto
import com.daangn.api.dto.users.UpdateUserRequestDto
import com.daangn.api.dto.users.UserResponseDto
import com.daangn.api.service.users.UserService
import com.daangn.api.service.users.query.UserQueryCreator
import com.daangn.domain.dto.Pagination
import com.daangn.domain.entity.users.UserOrderType
import com.daangn.domain.entity.users.UserQueryFilter
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

@ExtendWith(MockitoExtension::class)
@DisplayName("관리자 회원 컨트롤러 단위 테스트")
class AdminUserControllerTest {

    private lateinit var dummyUserResponse: UserResponseDto

    @Mock
    private lateinit var service: UserService

    @Mock
    private lateinit var queryCreator: UserQueryCreator

    @InjectMocks
    private lateinit var controller: AdminUserController

    @BeforeEach
    fun setup() {
        this.dummyUserResponse = DummyUser.toResponseDto()
    }

    @Test
    fun `회원 목록 조회`() {
        // given
        val queryFilter = UserQueryFilter(
            signUpType = null,
            role = null,
            resigned = null
        )
        val pagination = Pagination(
            page = 1,
            size = 20
        )
        val orderTypes: List<UserOrderType> = emptyList()
        val dummyUserResponses: List<UserResponseDto> = listOf(
            dummyUserResponse
        )
        val paginationResponse = PaginationResponseDto(
            page = pagination.page,
            size = pagination.size,
            data = dummyUserResponses,
            totalCount = dummyUserResponses.size.toLong()
        )
        `when`(
            queryCreator.createQueryFilter(
                signUpType = null,
                role = null,
                resigned = null
            )
        ).thenReturn(queryFilter)
        `when`(
            queryCreator.createPaginationFilter(
                page = null,
                size = null
            )
        ).thenReturn(pagination)
        `when`(
            service.getUsers(
                queryFilter = queryFilter,
                pagination = pagination,
                orderTypes = orderTypes
            )
        ).thenReturn(paginationResponse)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.getUsers(
            signUpType = null,
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
        val dummyUserId: String = dummyUserResponse.id
        `when`(
            service.getUser(
                userId = dummyUserId
            )
        ).thenReturn(dummyUserResponse)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.getUser(
            userId = dummyUserId
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(dummyUserResponse)
    }

    @Test
    fun `관리자 회원 정보 수정`() {
        // given
        val dummyUserId: String = dummyUserResponse.id
        val request: UpdateUserRequestDto = DummyUser.toUpdateRequestDto()
        `when`(
            service.update(
                userId = dummyUserId,
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
    fun `관리자 회원 탈퇴 처리`() {
        // given
        val dummyUserId: String = dummyUserResponse.id
        `when`(
            service.resign(
                userId = dummyUserId,
            )
        ).thenReturn(true)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.resign(
            userId = dummyUserId
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(true)
    }
}
