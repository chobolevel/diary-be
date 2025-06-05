package com.diary.api.users.points

import com.diary.api.users.DummyUser
import com.diary.domain.entity.users.User
import com.diary.domain.entity.users.points.UserPoint
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("회원 포인트 이력 엔티티 단위 테스트")
class UserPointEntityTest {

    private val dummyUserPoint: UserPoint = DummyUserPoint.toEntity()

    @Test
    fun `회원 포인트 이력 엔티티 회원 엔티티 매핑`() {
        // given
        val dummyUser: User = DummyUser.toEntity()

        // when
        dummyUserPoint.set(user = dummyUser)

        // then
        assertThat(dummyUserPoint.user).isEqualTo(dummyUser)
    }

    @Test
    fun `회원 포인트 이력 엔티티 삭제`() {
        // given

        // when
        dummyUserPoint.delete()

        // then
        assertThat(dummyUserPoint.deleted).isTrue()
    }
}
