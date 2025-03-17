package com.daangn.api.posts.likes

import com.daangn.api.controller.v1.common.posts.PostLikeController
import com.daangn.api.dto.common.PaginationResponseDto
import com.daangn.api.dto.common.ResultResponseDto
import com.daangn.api.dto.posts.likes.PostLikeResponseDto
import com.daangn.api.posts.DummyPost
import com.daangn.api.service.posts.PostLikeService
import com.daangn.api.service.posts.query.PostLikeQueryCreator
import com.daangn.api.users.DummyUser
import com.daangn.domain.dto.Pagination
import com.daangn.domain.entity.posts.Post
import com.daangn.domain.entity.posts.likes.PostLikeOrderType
import com.daangn.domain.entity.posts.likes.PostLikeQueryFilter
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
@DisplayName("게시글 좋아요 컨트롤런 단윝 테스트")
class PostLikeControllerTest {

    private lateinit var dummyPost: Post

    private lateinit var dummyUser: User
    private lateinit var dummyUserToken: UsernamePasswordAuthenticationToken

    private lateinit var dummyPostLikeResponse: PostLikeResponseDto

    @Mock
    private lateinit var service: PostLikeService

    @Mock
    private lateinit var queryCreator: PostLikeQueryCreator

    @InjectMocks
    private lateinit var controller: PostLikeController

    @BeforeEach
    fun setup() {
        this.dummyPost = DummyPost.toEntity()
        this.dummyUser = DummyUser.toEntity()
        this.dummyUserToken = DummyUser.toToken()
        this.dummyPostLikeResponse = DummyPostLike.toResponseDto()
    }

    @Test
    fun `게시글 좋아요 또는 좋아요 삭제 케이스`() {
        // given
        val dummyPostId: String = dummyPost.id
        val dummyUserId: String = dummyUser.id
        `when`(
            service.likeOrUnlike(
                userId = dummyUserId,
                postId = dummyPostId,
            )
        ).thenReturn(true)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.likeOrUnlike(
            principal = dummyUserToken,
            postId = dummyPostId
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(true)
    }

    @Test
    fun `게시글 좋아요 목록 조회`() {
        // given
        val queryFilter = PostLikeQueryFilter(
            postId = null,
            userId = null,
        )
        val pagination = Pagination(
            page = 1,
            size = 20
        )
        val orderTypes: List<PostLikeOrderType> = emptyList()
        val dummyPostLikeResponses: List<PostLikeResponseDto> = listOf(dummyPostLikeResponse)
        val paginationResponse = PaginationResponseDto(
            page = pagination.page,
            size = pagination.size,
            data = dummyPostLikeResponses,
            totalCount = dummyPostLikeResponses.size.toLong()
        )
        `when`(
            queryCreator.createQueryFilter(
                postId = null,
                userId = null
            )
        ).thenReturn(queryFilter)
        `when`(
            queryCreator.createPaginationFilter(
                page = null,
                size = null
            )
        ).thenReturn(pagination)
        `when`(
            service.getPostLikes(
                queryFilter = queryFilter,
                pagination = pagination,
                orderTypes = orderTypes
            )
        ).thenReturn(paginationResponse)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.getPostLikes(
            postId = null,
            userId = null,
            page = null,
            size = null,
            orderTypes = emptyList()
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(paginationResponse)
    }
}
