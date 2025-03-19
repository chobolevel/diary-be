package com.daangn.api.channels.messages

import com.daangn.api.channels.DummyChannel
import com.daangn.api.users.DummyUser
import com.daangn.domain.entity.channels.Channel
import com.daangn.domain.entity.channels.messages.ChannelMessage
import com.daangn.domain.entity.users.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("채널 메세지 엔티티 단위 테스트")
class ChannelMessageEntityTest {

    private lateinit var dummyChannelMessage: ChannelMessage

    @BeforeEach
    fun setup() {
        this.dummyChannelMessage = DummyChannelMessage.toEntity()
    }

    @Test
    fun `채널 메세지 엔티티 채널 엔티티 매핑 케이스`() {
        // given
        val dummyChannel: Channel = DummyChannel.toEntity()

        // when
        dummyChannelMessage.set(dummyChannel)

        // then
        assertThat(dummyChannelMessage.channel).isEqualTo(dummyChannel)
    }

    @Test
    fun `채널 메세지 엔티티 작성 회원 엔티티 매핑 케이스`() {
        // given
        val dummyUser: User = DummyUser.toEntity()

        // when
        dummyChannelMessage.set(dummyUser)

        // then
        assertThat(dummyChannelMessage.writer).isEqualTo(dummyUser)
    }

    @Test
    fun `채널 메세지 삭제 케이스`() {
        // given

        // when
        dummyChannelMessage.delete()

        // then
        assertThat(dummyChannelMessage.deleted).isTrue()
    }
}
