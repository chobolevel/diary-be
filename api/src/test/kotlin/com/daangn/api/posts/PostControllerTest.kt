package com.daangn.api.posts

import com.daangn.api.controller.v1.common.posts.PostController
import com.daangn.api.dto.common.PaginationResponseDto
import com.daangn.api.dto.common.ResultResponseDto
import com.daangn.api.dto.posts.CreatePostRequestDto
import com.daangn.api.dto.posts.PostResponseDto
import com.daangn.api.dto.posts.UpdatePostRequestDto
import com.daangn.api.service.posts.PostService
import com.daangn.api.service.posts.query.PostQueryCreator
import com.daangn.api.users.DummyUser
import com.daangn.domain.dto.Pagination
import com.daangn.domain.entity.posts.Post
import com.daangn.domain.entity.posts.PostOrderType
import com.daangn.domain.entity.posts.PostQueryFilter
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
@DisplayName("게시글 컨트롤러 단위 테스트")
class PostControllerTest {

    private lateinit var dummyUser: User
    private lateinit var dummyUserToken: UsernamePasswordAuthenticationToken

    private lateinit var dummyPost: Post
    private lateinit var dummyPostResponse: PostResponseDto

    @Mock
    private lateinit var service: PostService

    @Mock
    private lateinit var queryCreator: PostQueryCreator

    @InjectMocks
    private lateinit var controller: PostController

    @BeforeEach
    fun setup() {
        dummyUser = DummyUser.toEntity()
        dummyUserToken = DummyUser.toToken()
        dummyPost = DummyPost.toEntity()
        dummyPostResponse = DummyPost.toResponseDto()
    }

    @Test
    fun `게시글 등록`() {
        // given
        val dummyUserId: String = dummyUser.id
        val dummyPostId: String = dummyPost.id
        val request: CreatePostRequestDto = DummyPost.toCreateRequestDto()
        `when`(
            service.create(
                userId = dummyUserId,
                request = request
            )
        ).thenReturn(dummyPostId)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.create(
            principal = dummyUserToken,
            request = request
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.CREATED)
        assertThat(result.body?.data).isEqualTo(dummyPostId)
    }

    @Test
    fun `게시글 목록 조회`() {
        // given
        val queryFilter = PostQueryFilter(
            writerId = null,
            categoryId = null,
            title = null
        )
        val pagination = Pagination(
            page = 1,
            size = 20
        )
        val orderTypes: List<PostOrderType> = emptyList()
        val dummyPostResponses: List<PostResponseDto> = listOf(
            dummyPostResponse
        )
        val paginationResponse = PaginationResponseDto(
            page = pagination.page,
            size = pagination.size,
            data = dummyPostResponses,
            totalCount = dummyPostResponses.size.toLong()
        )
        `when`(
            queryCreator.createQueryFilter(
                writerId = null,
                categoryId = null,
                title = null
            )
        ).thenReturn(queryFilter)
        `when`(
            queryCreator.createPaginationFilter(
                page = null,
                size = null
            )
        ).thenReturn(pagination)
        `when`(
            service.getPosts(
                queryFilter = queryFilter,
                pagination = pagination,
                orderTypes = orderTypes
            )
        ).thenReturn(paginationResponse)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.getPosts(
            writerId = null,
            categoryId = null,
            title = null,
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
    fun `게시글 단건 조회`() {
        // given
        val dummyPostId: String = dummyPost.id
        `when`(
            service.getPost(
                postId = dummyPostId
            )
        ).thenReturn(dummyPostResponse)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.getPost(
            postId = dummyPostId
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(dummyPostResponse)
    }

    @Test
    fun `게시글 수정`() {
        // given
        val dummyUserId: String = dummyUser.id
        val dummyPostId: String = dummyPost.id
        val request: UpdatePostRequestDto = DummyPost.toUpdateRequestDto()
        `when`(
            service.update(
                userId = dummyUserId,
                postId = dummyPostId,
                request = request
            )
        ).thenReturn(dummyPostId)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.update(
            principal = dummyUserToken,
            postId = dummyPostId,
            request = request
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(dummyPostId)
    }

    @Test
    fun `게시글 삭제`() {
        // given
        val dummyUserId: String = dummyUser.id
        val dummyPostId: String = dummyPost.id
        `when`(
            service.delete(
                userId = dummyUserId,
                postId = dummyPostId
            )
        ).thenReturn(true)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.delete(
            principal = dummyUserToken,
            postId = dummyPostId
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(true)
    }
}
