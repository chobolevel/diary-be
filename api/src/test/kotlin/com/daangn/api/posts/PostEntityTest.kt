package com.daangn.api.posts

import com.daangn.api.categories.DummyCategory
import com.daangn.api.users.DummyUser
import com.daangn.domain.entity.posts.Post
import com.daangn.domain.entity.users.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("게시글 엔티티 단위 테스트")
class PostEntityTest {

    private lateinit var dummyPost: Post

    @BeforeEach
    fun setup() {
        dummyPost = DummyPost.toEntity()
    }

    @Test
    fun `게시글 엔티티 작성자 매핑 케이스`() {
        // given
        val dummyUser: User = DummyUser.toEntity()

        // when
        dummyPost.set(dummyUser)

        // then
        assertThat(dummyPost.writer).isEqualTo(dummyUser)
    }

    @Test
    fun `게시글 엔티티 카테고리 매핑 케이스`() {
        // given
        val dummyCategory = DummyCategory.toEntity()

        // when
        dummyPost.set(dummyCategory)

        // then
        assertThat(dummyPost.category).isEqualTo(dummyCategory)
    }

    @Test
    fun `게시글 엔티티 삭제 케이스`() {
        // given

        // when
        dummyPost.delete()

        // then
        assertThat(dummyPost.deleted).isTrue()
    }
}
