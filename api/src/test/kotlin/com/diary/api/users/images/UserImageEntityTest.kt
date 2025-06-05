package com.diary.api.users.images

import com.diary.api.users.DummyUser
import com.diary.domain.entity.users.User
import com.diary.domain.entity.users.images.UserImage
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("회원 이미지 엔티티 단위 테스트")
class UserImageEntityTest {

    private val dummyUserImage: UserImage = DummyUserImage.toEntity()

    @Test
    fun `회원 이미지 엔티티 회원 엔티티 매핑`() {
        // given
        val dummyUser: User = DummyUser.toEntity()

        // when
        dummyUserImage.set(user = dummyUser)

        // then
        assertThat(dummyUserImage.user).isEqualTo(dummyUser)
        assertThat(dummyUser.images.get(0)).isEqualTo(dummyUserImage)
    }

    @Test
    fun `회원 이미지 엔티티 삭제`() {
        // given

        // when
        dummyUserImage.delete()

        // then
        assertThat(dummyUserImage.deleted).isTrue()
    }
}
