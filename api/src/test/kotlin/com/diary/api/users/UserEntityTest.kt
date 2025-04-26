package com.diary.api.users

import com.diary.domain.entity.users.User
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("회원 엔티티 단위 테스트")
class UserEntityTest {

    private val dummyUser: User = DummyUser.toEntity()

    @Test
    fun `회원 탈퇴`() {
        // given

        // when
        dummyUser.resign()

        // then
        Assertions.assertThat(dummyUser.resigned).isTrue()
    }
}
