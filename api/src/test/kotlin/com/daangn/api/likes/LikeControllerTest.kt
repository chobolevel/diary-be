package com.daangn.api.likes

import com.daangn.api.controller.v1.common.likes.LikeController
import com.daangn.api.dto.common.PaginationResponseDto
import com.daangn.api.dto.common.ResultResponseDto
import com.daangn.api.dto.likes.LikeRequestDto
import com.daangn.api.dto.likes.LikeResponseDto
import com.daangn.api.service.likes.LikeService
import com.daangn.api.service.likes.query.LikeQueryCreator
import com.daangn.api.users.DummyUser
import com.daangn.domain.dto.Pagination
import com.daangn.domain.entity.likes.Like
import com.daangn.domain.entity.likes.LikeOrderType
import com.daangn.domain.entity.likes.LikeQueryFilter
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
@DisplayName("좋아요 컨트롤런 단위 테스트")
class LikeControllerTest {

    private lateinit var dummyUser: User
    private lateinit var dummyUserToken: UsernamePasswordAuthenticationToken

    private lateinit var dummyLike: Like
    private lateinit var dummyLikeResponse: LikeResponseDto

    @Mock
    private lateinit var service: LikeService

    @Mock
    private lateinit var queryCreator: LikeQueryCreator

    @InjectMocks
    private lateinit var controller: LikeController

    @BeforeEach
    fun setup() {
        this.dummyUser = DummyUser.toEntity()
        this.dummyUserToken = DummyUser.toToken()
        this.dummyLike = DummyLike.toEntity()
        this.dummyLikeResponse = DummyLike.toResponseDto()
    }

    @Test
    fun `좋아요 좋아요 제거`() {
        // given
        val request: LikeRequestDto = DummyLike.toRequestDto()
        `when`(
            service.likeOrUnlike(
                userId = dummyUser.id,
                request = request,
            )
        ).thenReturn(true)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.likeOrUnlike(
            principal = dummyUserToken,
            request = request
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(true)
    }

    @Test
    fun `좋아요 목록 조회`() {
        // given
        val dummyUserId: String = dummyUser.id
        val queryFilter = LikeQueryFilter(
            userId = dummyUserId,
        )
        val pagination = Pagination(
            page = 1,
            size = 20
        )
        val orderTypes: List<LikeOrderType> = emptyList()
        val dummyLikeResponses: List<LikeResponseDto> = listOf(dummyLikeResponse)
        val paginationResponse = PaginationResponseDto(
            page = pagination.page,
            size = pagination.size,
            data = dummyLikeResponses,
            totalCount = dummyLikeResponses.size.toLong()
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
            service.getLikes(
                queryFilter = queryFilter,
                pagination = pagination,
                orderTypes = orderTypes
            )
        ).thenReturn(paginationResponse)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.getLikes(
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
}
