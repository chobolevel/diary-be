package com.daangn.api.users.regions

import com.daangn.api.controller.v1.common.users.UserRegionController
import com.daangn.api.dto.common.PaginationResponseDto
import com.daangn.api.dto.common.ResultResponseDto
import com.daangn.api.dto.users.regions.CreateUserRegionRequestDto
import com.daangn.api.dto.users.regions.UpdateUserRegionRequestDto
import com.daangn.api.dto.users.regions.UserRegionResponseDto
import com.daangn.api.service.users.UserRegionService
import com.daangn.api.service.users.query.UserRegionQueryCreator
import com.daangn.api.users.DummyUser
import com.daangn.domain.dto.Pagination
import com.daangn.domain.entity.users.User
import com.daangn.domain.entity.users.regions.UserRegionOrderType
import com.daangn.domain.entity.users.regions.UserRegionQueryFilter
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
@DisplayName("회원 지역 컨트롤러 단위 테스트")
class UserRegionControllerTest {

    private lateinit var dummyUser: User
    private lateinit var dummyUserToken: UsernamePasswordAuthenticationToken
    private lateinit var dummyUserRegionResponse: UserRegionResponseDto

    @Mock
    private lateinit var service: UserRegionService

    @Mock
    private lateinit var queryCreator: UserRegionQueryCreator

    @InjectMocks
    private lateinit var controller: UserRegionController

    @BeforeEach
    fun setup() {
        dummyUser = DummyUser.toEntity()
        dummyUserToken = DummyUser.toToken()
        dummyUserRegionResponse = DummyUserRegion.toResponseDto()
    }

    @Test
    fun `회원 지역 등록`() {
        // given
        val dummyUserId: String = dummyUser.id
        val dummyUserRegionId: String = dummyUserRegionResponse.id
        val request: CreateUserRegionRequestDto = DummyUserRegion.toCreateRequestDto()
        `when`(
            service.create(
                userId = dummyUserId,
                request = request
            )
        ).thenReturn(dummyUserRegionId)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.create(
            principal = dummyUserToken,
            request = request
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.CREATED)
        assertThat(result.body?.data).isEqualTo(dummyUserRegionId)
    }

    @Test
    fun `회원 지역 목록 조회`() {
        // given
        val dummyUserId: String = dummyUser.id
        val queryFilter = UserRegionQueryFilter(
            userId = dummyUserId
        )
        val pagination = Pagination(
            page = 1,
            size = 20
        )
        val orderTypes: List<UserRegionOrderType> = emptyList()
        val dummyUserRegionResponses: List<UserRegionResponseDto> = listOf(
            dummyUserRegionResponse,
        )
        val paginationResponse = PaginationResponseDto(
            page = pagination.page,
            size = pagination.size,
            data = dummyUserRegionResponses,
            totalCount = dummyUserRegionResponses.size.toLong()
        )
        `when`(
            queryCreator.createQueryFilter(
                userId = dummyUserId
            )
        ).thenReturn(queryFilter)
        `when`(
            queryCreator.createPaginationFilter(
                page = null,
                size = null
            )
        ).thenReturn(pagination)
        `when`(
            service.getUserRegions(
                queryFilter = queryFilter,
                pagination = pagination,
                orderTypes = orderTypes
            )
        ).thenReturn(paginationResponse)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.getUserRegions(
            principal = dummyUserToken,
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
    fun `회원 지역 단건 조회`() {
        // given
        val dummyUserRegionId: String = dummyUserRegionResponse.id
        `when`(
            service.getUserRegion(
                userRegionId = dummyUserRegionId
            )
        ).thenReturn(dummyUserRegionResponse)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.getUserRegion(
            principal = dummyUserToken,
            userRegionId = dummyUserRegionId
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(dummyUserRegionResponse)
    }

    @Test
    fun `회원 지역 정보 수정`() {
        // given
        val dummyUserId: String = dummyUser.id
        val dummyUserRegionId: String = dummyUserRegionResponse.id
        val request: UpdateUserRegionRequestDto = DummyUserRegion.toUpdateRequestDto()
        `when`(
            service.update(
                userId = dummyUserId,
                userRegionId = dummyUserRegionId,
                request = request
            )
        ).thenReturn(dummyUserRegionId)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.update(
            principal = dummyUserToken,
            userRegionId = dummyUserRegionId,
            request = request
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(dummyUserRegionId)
    }

    @Test
    fun `회원 지역 정보 삭제`() {
        // given
        val dummyUserId: String = dummyUser.id
        val dummyUserRegionId: String = dummyUserRegionResponse.id
        `when`(
            service.delete(
                userId = dummyUserId,
                userRegionId = dummyUserRegionId
            )
        ).thenReturn(true)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.delete(
            principal = dummyUserToken,
            userRegionId = dummyUserRegionId
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(true)
    }
}
