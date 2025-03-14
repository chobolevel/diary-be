package com.daangn.api.posts.image

import com.daangn.api.posts.DummyPost
import com.daangn.domain.entity.posts.Post
import com.daangn.domain.entity.posts.image.PostImage
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("게시글 이미지 엔티티 단위 테스트")
class PostImageEntityTest {

    private lateinit var dummyPostImage: PostImage

    @BeforeEach
    fun setup() {
        dummyPostImage = DummyPostImage.toEntity()
    }

    @Test
    fun `게시글 이미지 엔티티 게시글 엔티티 매핑 케이스`() {
        // given
        val dummyPost: Post = DummyPost.toEntity()

        // when
        dummyPostImage.set(dummyPost)

        // then
        assertThat(dummyPostImage.post).isEqualTo(dummyPost)
    }

    @Test
    fun `게시글 이미지 삭제 케이스`() {
        // given

        // when
        dummyPostImage.delete()

        // then
        assertThat(dummyPostImage.deleted).isTrue()
    }
}
