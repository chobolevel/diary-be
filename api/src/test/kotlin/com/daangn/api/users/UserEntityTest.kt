package com.daangn.api.users

import com.daangn.domain.entity.users.User
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("회원 엔티티 단위 테스트")
class UserEntityTest {

    private lateinit var dummyUser: User

    @BeforeEach
    fun setup() {
        dummyUser = DummyUser.toEntity()
    }

    @Test
    fun `회원 탈퇴 케이스`() {
        // given

        // when
        dummyUser.resign()

        // then
        Assertions.assertThat(dummyUser.resigned).isTrue()
    }
}
