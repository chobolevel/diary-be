package com.daangn.api.posts.likes

import com.daangn.api.dto.common.PaginationResponseDto
import com.daangn.api.dto.posts.likes.PostLikeResponseDto
import com.daangn.api.posts.DummyPost
import com.daangn.api.service.posts.PostLikeService
import com.daangn.api.service.posts.converter.PostLikeConverter
import com.daangn.api.users.DummyUser
import com.daangn.domain.dto.Pagination
import com.daangn.domain.entity.posts.Post
import com.daangn.domain.entity.posts.PostRepositoryWrapper
import com.daangn.domain.entity.posts.likes.PostLike
import com.daangn.domain.entity.posts.likes.PostLikeOrderType
import com.daangn.domain.entity.posts.likes.PostLikeQueryFilter
import com.daangn.domain.entity.posts.likes.PostLikeRepositoryWrapper
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
@DisplayName("게시글 좋아요 서비스 단위 테스트")
class PostLikeServiceTest {

    private lateinit var dummyPost: Post

    private lateinit var dummyPostLike: PostLike
    private lateinit var dummyPostLikeResponse: PostLikeResponseDto

    private lateinit var dummyUser: User

    @Mock
    private lateinit var repositoryWrapper: PostLikeRepositoryWrapper

    @Mock
    private lateinit var converter: PostLikeConverter

    @Mock
    private lateinit var postRepositoryWrapper: PostRepositoryWrapper

    @Mock
    private lateinit var userRepositoryWrapper: UserRepositoryWrapper

    @InjectMocks
    private lateinit var service: PostLikeService

    @BeforeEach
    fun setup() {
        this.dummyPost = DummyPost.toEntity()
        this.dummyPostLike = DummyPostLike.toEntity()
        this.dummyPostLikeResponse = DummyPostLike.toResponseDto()
        this.dummyUser = DummyUser.toEntity()
    }

    @Test
    fun `게시글 좋아요 또는 좋아요 삭제 케이스`() {
        // given
        val dummyPostId: String = dummyPost.id
        val dummyUserId: String = dummyUser.id
        `when`(
            repositoryWrapper.findByPostIdAndUserId(
                postId = dummyPostId,
                userId = dummyUserId
            )
        ).thenReturn(null)
        `when`(converter.convert()).thenReturn(dummyPostLike)
        `when`(postRepositoryWrapper.findById(dummyPostId)).thenReturn(dummyPost)
        `when`(userRepositoryWrapper.findById(dummyUserId)).thenReturn(dummyUser)
        `when`(repositoryWrapper.save(dummyPostLike)).thenReturn(dummyPostLike)

        // when
        val result: Boolean = service.likeOrUnlike(
            userId = dummyUserId,
            postId = dummyPostId
        )

        // then
        assertThat(result).isTrue()
    }

    @Test
    fun `게시글 좋아요 목록 조회`() {
        // given
        val queryFilter = PostLikeQueryFilter(
            postId = null,
            userId = null
        )
        val pagination = Pagination(
            page = 1,
            size = 20
        )
        val orderTypes: List<PostLikeOrderType> = emptyList()
        val dummyPostLikes: List<PostLike> = listOf(dummyPostLike)
        val dummyPostLikeResponses: List<PostLikeResponseDto> = listOf(dummyPostLikeResponse)
        `when`(
            repositoryWrapper.search(
                queryFilter = queryFilter,
                pagination = pagination,
                orderTypes = orderTypes
            )
        ).thenReturn(dummyPostLikes)
        `when`(
            repositoryWrapper.searchCount(
                queryFilter = queryFilter
            )
        ).thenReturn(dummyPostLikes.size.toLong())
        `when`(converter.convert(dummyPostLikes)).thenReturn(dummyPostLikeResponses)

        // when
        val result: PaginationResponseDto = service.getPostLikes(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes
        )

        // then
        assertThat(result.page).isEqualTo(pagination.page)
        assertThat(result.size).isEqualTo(pagination.size)
        assertThat(result.data).isEqualTo(dummyPostLikeResponses)
        assertThat(result.totalCount).isEqualTo(dummyPostLikeResponses.size.toLong())
    }
}
