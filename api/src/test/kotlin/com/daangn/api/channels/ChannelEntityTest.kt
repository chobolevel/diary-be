package com.daangn.api.channels

import com.daangn.domain.entity.channels.Channel
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("채널 엔티티 단위 테스트")
class ChannelEntityTest {

    private lateinit var dummyChannel: Channel

    @BeforeEach
    fun setup() {
        this.dummyChannel = DummyChannel.toEntity()
    }

    @Test
    fun `채널 삭제`() {
        // given

        // when
        dummyChannel.delete()

        // then
        assertThat(dummyChannel.deleted).isTrue()
    }
}
