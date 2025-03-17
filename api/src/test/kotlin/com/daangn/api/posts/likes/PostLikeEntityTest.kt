package com.daangn.api.posts.likes

import com.daangn.api.posts.DummyPost
import com.daangn.api.users.DummyUser
import com.daangn.domain.entity.posts.Post
import com.daangn.domain.entity.posts.likes.PostLike
import com.daangn.domain.entity.users.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("게시글 좋아요 엔티티 단위 테스트")
class PostLikeEntityTest {

    private lateinit var dummyPostLike: PostLike

    @BeforeEach
    fun setup() {
        dummyPostLike = DummyPostLike.toEntity()
    }

    @Test
    fun `게시글 좋아요 게시글 매핑 케이스`() {
        // given
        val dummyPost: Post = DummyPost.toEntity()

        // when
        dummyPostLike.set(dummyPost)

        // then
        assertThat(dummyPostLike.post).isEqualTo(dummyPost)
    }

    @Test
    fun `게시글 좋아요 회원 매핑 케이스`() {
        // given
        val dummyUser: User = DummyUser.toEntity()

        // when
        dummyPostLike.set(dummyUser)

        // then
        assertThat(dummyPostLike.user).isEqualTo(dummyUser)
    }

    @Test
    fun `게시글 좋아요 삭제 케이스`() {
        // given

        // when
        dummyPostLike.delete()

        // then
        assertThat(dummyPostLike.deleted).isTrue()
    }
}
