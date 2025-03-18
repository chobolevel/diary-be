package com.daangn.api.channels.users

import com.daangn.api.channels.DummyChannel
import com.daangn.api.users.DummyUser
import com.daangn.domain.entity.channels.Channel
import com.daangn.domain.entity.channels.users.ChannelUser
import com.daangn.domain.entity.users.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("채널 회원 엔티티 단위 테스트")
class ChannelUserEntityTest {

    private lateinit var dummyChannelUser: ChannelUser

    @BeforeEach
    fun setup() {
        this.dummyChannelUser = DummyChannelUser.toEntity()
    }

    @Test
    fun `채널 회원 엔티티 채널 매핑 케이스`() {
        // given
        val dummyChannel: Channel = DummyChannel.toEntity()

        // when
        dummyChannelUser.set(dummyChannel)

        // then
        assertThat(dummyChannelUser.channel).isEqualTo(dummyChannel)
    }

    @Test
    fun `채널 회원 엔티티 회원 매핑 케이스`() {
        // given
        val dummyUser: User = DummyUser.toEntity()

        // when
        dummyChannelUser.set(dummyUser)

        // then
        assertThat(dummyChannelUser.user).isEqualTo(dummyUser)
    }

    @Test
    fun `채널 회원 삭제 케이스`() {
        // given

        // when
        dummyChannelUser.delete()

        // then
        assertThat(dummyChannelUser.deleted).isTrue()
    }
}
