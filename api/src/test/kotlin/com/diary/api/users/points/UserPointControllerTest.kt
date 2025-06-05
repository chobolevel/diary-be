package com.diary.api.users.points

import com.diary.api.controller.v1.common.users.UserPointController
import com.diary.api.dto.common.PaginationResponseDto
import com.diary.api.dto.common.ResultResponseDto
import com.diary.api.dto.users.points.UserPointResponseDto
import com.diary.api.service.users.UserPointService
import com.diary.api.users.DummyUser
import com.diary.domain.dto.Pagination
import com.diary.domain.entity.users.User
import com.diary.domain.entity.users.points.UserPointOrderType
import com.diary.domain.entity.users.points.UserPointQueryFilter
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
@DisplayName("회원 포인트 컨트롤러 단위 테스트")
class UserPointControllerTest {

    private val dummyUser: User = DummyUser.toEntity()
    private val dummyUserToken: UsernamePasswordAuthenticationToken = DummyUser.toToken()

    private val dummyUserPointResponse: UserPointResponseDto = DummyUserPoint.toResponseDto()

    @Mock
    private lateinit var service: UserPointService

    @InjectMocks
    private lateinit var controller: UserPointController

    @Test
    fun `회원 포인트 이력 목록 조회`() {
        // given
        val dummyUserId: ID = dummyUser.id
        val queryFilter = UserPointQueryFilter(
            userId = dummyUserId,
            type = null
        )
        val pagination = Pagination(
            page = 1,
            size = 10
        )
        val orderTypes: List<UserPointOrderType> = emptyList()
        val dummyUserPointResponses: List<UserPointResponseDto> = listOf(dummyUserPointResponse)
        val paginationResponse = PaginationResponseDto(
            page = pagination.page,
            size = pagination.size,
            data = dummyUserPointResponses,
            totalCount = dummyUserPointResponses.size.toLong()
        )
        `when`(
            service.getUserPoints(
                queryFilter = queryFilter,
                pagination = pagination,
                orderTypes = orderTypes
            )
        ).thenReturn(paginationResponse)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.getUserPoints(
            principal = dummyUserToken,
            type = null,
            page = null,
            size = null,
            orderTypes = null
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(paginationResponse)
    }
}
