package com.diary.api.emotions

import com.diary.domain.entity.emotions.Emotion
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("감정 엔티티 단위 테스트")
class EmotionEntityTest {

    private val dummyEmotion: Emotion = DummyEmotion.toEntity()

    @Test
    fun `감정 엔티티 삭제`() {
        // given

        // when
        dummyEmotion.delete()

        // then
        assertThat(dummyEmotion.deleted).isTrue()
    }
}
