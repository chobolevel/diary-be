package com.daangn.api.likes

import com.daangn.api.dto.common.PaginationResponseDto
import com.daangn.api.dto.likes.LikeRequestDto
import com.daangn.api.dto.likes.LikeResponseDto
import com.daangn.api.posts.DummyPost
import com.daangn.api.service.likes.LikeService
import com.daangn.api.service.likes.converter.LikeConverter
import com.daangn.api.service.likes.validator.LikeValidator
import com.daangn.api.users.DummyUser
import com.daangn.domain.dto.Pagination
import com.daangn.domain.entity.likes.Like
import com.daangn.domain.entity.likes.LikeOrderType
import com.daangn.domain.entity.likes.LikeQueryFilter
import com.daangn.domain.entity.likes.LikeRepositoryWrapper
import com.daangn.domain.entity.posts.Post
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
import org.mockito.Mockito.anyString
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
@DisplayName("좋아요 서비스 로직 단위 테스트")
class LikeServiceTest {

    private lateinit var dummyUser: User

    private lateinit var dummyPost: Post

    private lateinit var dummyLike: Like
    private lateinit var dummyLikeResponse: LikeResponseDto

    @Mock
    private lateinit var repositoryWrapper: LikeRepositoryWrapper

    @Mock
    private lateinit var userRepositoryWrapper: UserRepositoryWrapper

    @Mock
    private lateinit var postRepositoryWrapper: PostRepositoryWrapper

    @Mock
    private lateinit var converter: LikeConverter

    @Mock
    private lateinit var validator: LikeValidator

    @InjectMocks
    private lateinit var service: LikeService

    @BeforeEach
    fun setup() {
        this.dummyUser = DummyUser.toEntity()
        this.dummyPost = DummyPost.toEntity()
        this.dummyLike = DummyLike.toEntity()
        this.dummyLikeResponse = DummyLike.toResponseDto()
    }

    @Test
    fun `좋아요 또는 좋아요 제거`() {
        // given
        val dummyUserId: String = dummyUser.id
        val request: LikeRequestDto = DummyLike.toRequestDto()
        `when`(
            repositoryWrapper.findByUserIdAndTargetId(
                userId = dummyUserId,
                targetId = request.targetId
            )
        ).thenReturn(null)
        doNothing().`when`(validator).validate(request)
        `when`(converter.convert(request)).thenReturn(dummyLike)
        `when`(userRepositoryWrapper.findById(dummyUserId)).thenReturn(dummyUser)
        `when`(postRepositoryWrapper.findById(anyString())).thenReturn(dummyPost)
        `when`(repositoryWrapper.save(dummyLike)).thenReturn(dummyLike)

        // when
        val result: Boolean = service.likeOrUnlike(
            userId = dummyUser.id,
            request = request
        )

        // then
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun `좋아요 목록 조회`() {
        // given
        val queryFilter = LikeQueryFilter(
            userId = null
        )
        val pagination = Pagination(
            page = 1,
            size = 20
        )
        val orderTypes: List<LikeOrderType> = emptyList()
        val dummyLikes: List<Like> = listOf(dummyLike)
        val dummyLikeResponses: List<LikeResponseDto> = listOf(dummyLikeResponse)
        `when`(
            repositoryWrapper.search(
                queryFilter = queryFilter,
                pagination = pagination,
                orderTypes = orderTypes,
            )
        ).thenReturn(dummyLikes)
        `when`(
            repositoryWrapper.searchCount(
                queryFilter = queryFilter
            )
        ).thenReturn(dummyLikes.size.toLong())
        `when`(converter.convert(dummyLikes)).thenReturn(dummyLikeResponses)

        // when
        val result: PaginationResponseDto = service.getLikes(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes
        )

        // then
        assertThat(result.page).isEqualTo(pagination.page)
        assertThat(result.size).isEqualTo(pagination.size)
        assertThat(result.data).isEqualTo(dummyLikeResponses)
        assertThat(result.totalCount).isEqualTo(dummyLikeResponses.size.toLong())
    }
}
