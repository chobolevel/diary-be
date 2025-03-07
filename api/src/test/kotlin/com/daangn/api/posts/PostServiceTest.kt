package com.daangn.api.posts

import com.daangn.api.categories.DummyCategory
import com.daangn.api.dto.common.PaginationResponseDto
import com.daangn.api.dto.posts.CreatePostRequestDto
import com.daangn.api.dto.posts.PostResponseDto
import com.daangn.api.dto.posts.UpdatePostRequestDto
import com.daangn.api.service.posts.PostService
import com.daangn.api.service.posts.converter.PostConverter
import com.daangn.api.service.posts.updater.PostUpdater
import com.daangn.api.users.DummyUser
import com.daangn.domain.dto.Pagination
import com.daangn.domain.entity.categories.Category
import com.daangn.domain.entity.categories.CategoryRepositoryWrapper
import com.daangn.domain.entity.posts.Post
import com.daangn.domain.entity.posts.PostOrderType
import com.daangn.domain.entity.posts.PostQueryFilter
import com.daangn.domain.entity.posts.PostRepositoryWrapper
import com.daangn.domain.entity.users.User
import com.daangn.domain.entity.users.UserRepositoryWrapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
@DisplayName("게시글 서비스 로직 단위 테스트")
class PostServiceTest {

    private lateinit var dummyUser: User

    private lateinit var dummyCategory: Category

    private lateinit var dummyPost: Post
    private lateinit var dummyPostResponse: PostResponseDto

    @Mock
    private lateinit var repositoryWrapper: PostRepositoryWrapper

    @Mock
    private lateinit var userRepositoryWrapper: UserRepositoryWrapper

    @Mock
    private lateinit var categoryRepositoryWrapper: CategoryRepositoryWrapper

    @Mock
    private lateinit var converter: PostConverter

    @Mock
    private lateinit var updater: PostUpdater

    @InjectMocks
    private lateinit var service: PostService

    @BeforeEach
    fun setup() {
        dummyUser = DummyUser.toEntity()
        dummyCategory = DummyCategory.toEntity()
        dummyPost = DummyPost.toEntity()
        dummyPostResponse = DummyPost.toResponseDto()
    }

    @Test
    fun `게시글 등록`() {
        // given
        val dummyUserId: String = dummyUser.id
        val dummyPostId: String = dummyPost.id
        val request: CreatePostRequestDto = DummyPost.toCreateRequestDto()
        `when`(converter.convert(request)).thenReturn(dummyPost)
        `when`(userRepositoryWrapper.findById(dummyUserId)).thenReturn(dummyUser)
        `when`(categoryRepositoryWrapper.findById(request.categoryId)).thenReturn(dummyCategory)
        `when`(repositoryWrapper.save(dummyPost)).thenReturn(dummyPost)

        // when
        val result: String = service.create(
            userId = dummyUserId,
            request = request
        )

        // then
        assertThat(result).isEqualTo(dummyPostId)
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
        val dummyPosts: List<Post> = listOf(
            dummyPost
        )
        val dummyPostResponses: List<PostResponseDto> = listOf(
            dummyPostResponse
        )
        `when`(
            repositoryWrapper.search(
                queryFilter = queryFilter,
                pagination = pagination,
                orderTypes = orderTypes
            )
        ).thenReturn(dummyPosts)
        `when`(
            repositoryWrapper.searchCount(
                queryFilter = queryFilter
            )
        ).thenReturn(dummyPosts.size.toLong())
        `when`(converter.convert(dummyPosts)).thenReturn(dummyPostResponses)

        // when
        val result: PaginationResponseDto = service.getPosts(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes
        )

        // then
        assertThat(result.page).isEqualTo(pagination.page)
        assertThat(result.size).isEqualTo(pagination.size)
        assertThat(result.data).isEqualTo(dummyPostResponses)
        assertThat(result.totalCount).isEqualTo(dummyPosts.size.toLong())
    }

    @Test
    fun `게시글 단건 조회`() {
        // given
        val dummyPostId: String = dummyPost.id
        `when`(repositoryWrapper.findById(dummyPostId)).thenReturn(dummyPost)
        `when`(converter.convert(dummyPost)).thenReturn(dummyPostResponse)

        // when
        val result: PostResponseDto = service.getPost(
            postId = dummyPostId
        )

        // then
        assertThat(result).isEqualTo(dummyPostResponse)
    }

    @Test
    fun `게시글 수정`() {
        // given
        val dummyUserId: String = dummyUser.id
        val dummyPostId: String = dummyPost.id
        val request: UpdatePostRequestDto = DummyPost.toUpdateRequestDto()
        `when`(repositoryWrapper.findById(dummyPostId)).thenReturn(dummyPost)
        `when`(
            updater.markAsUpdate(
                request = request,
                entity = dummyPost
            )
        ).thenReturn(dummyPost)

        // when
        val result: String = service.update(
            userId = dummyUserId,
            postId = dummyPostId,
            request = request
        )

        // then
        assertThat(result).isEqualTo(dummyPostId)
    }

    @Test
    fun `게시글 삭제`() {
        // given
        val dummyUserId: String = dummyUser.id
        val dummyPostId: String = dummyPost.id
        `when`(repositoryWrapper.findById(dummyPostId)).thenReturn(dummyPost)

        // when
        val result: Boolean = service.delete(
            userId = dummyUserId,
            postId = dummyPostId
        )

        // then
        assertThat(result).isTrue()
    }
}
