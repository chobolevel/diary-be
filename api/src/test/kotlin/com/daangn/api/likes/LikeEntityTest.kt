package com.daangn.api.likes

import com.daangn.api.users.DummyUser
import com.daangn.domain.entity.likes.Like
import com.daangn.domain.entity.users.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("좋아요 엔티티 단위 테스트")
class LikeEntityTest {

    private lateinit var dummyLike: Like

    @BeforeEach
    fun setup() {
        this.dummyLike = DummyLike.toEntity()
    }

    @Test
    fun `좋아요 엔티티 회원 엔티티 매핑 케이스`() {
        // given
        val dummyUser: User = DummyUser.toEntity()

        // when
        dummyLike.set(dummyUser)

        // then
        assertThat(dummyLike.user).isEqualTo(dummyUser)
    }

    @Test
    fun `좋아요 엔티티 삭제 케이스`() {
        // given

        // when
        dummyLike.delete()

        // then
        assertThat(dummyLike.deleted).isTrue()
    }
}
