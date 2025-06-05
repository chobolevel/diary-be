package com.diary.api.users

import com.diary.domain.entity.users.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("회원 엔티티 단위 테스트")
class UserEntityTest {

    private val dummyUser: User = DummyUser.toEntity()

    @Test
    fun `비밀번호 변경`() {
        // given
        val newEncodedPassword: String = "jik584697@"

        // when
        dummyUser.changePassword(newEncodedPassword = newEncodedPassword)

        // then
        assertThat(dummyUser.password).isEqualTo(newEncodedPassword)
    }

    @Test
    fun `포인트 지급`() {
        // given
        dummyUser.point = 0
        val amount: Int = 10000

        // when
        dummyUser.addPoint(amount = amount)

        // then
        assertThat(dummyUser.point).isEqualTo(amount)
    }

    @Test
    fun `포인트 회수`() {
        // given
        dummyUser.point = 0
        val amount: Int = 10000

        // when
        dummyUser.subPoint(amount = amount)

        // then
        assertThat(dummyUser.point).isEqualTo(-amount)
    }

    @Test
    fun `회원 탈퇴`() {
        // given

        // when
        dummyUser.resign()

        // then
        assertThat(dummyUser.resigned).isTrue()
    }
}
