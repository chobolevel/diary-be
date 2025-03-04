package com.daangn.api.users.regions

import com.daangn.api.users.DummyUser
import com.daangn.domain.entity.users.regions.UserRegion
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("회원 지역 엔티티 단위 테스트")
class UserRegionEntityTest {

    private lateinit var dummyUserRegion: UserRegion

    @BeforeEach
    fun setup() {
        dummyUserRegion = DummyUserRegion.toEntity()
    }

    @Test
    fun `회원 지역 엔티티 회원 매핑`() {
        // given
        val dummyUser = DummyUser.toEntity()

        // when
        dummyUserRegion.set(dummyUser)

        // then
        assertThat(dummyUserRegion.user).isEqualTo(dummyUser)
    }

    @Test
    fun `회원 지역 엔티티 삭제`() {
        // given

        // when
        dummyUserRegion.delete()

        // then
        assertThat(dummyUserRegion.deleted).isTrue()
    }
}
