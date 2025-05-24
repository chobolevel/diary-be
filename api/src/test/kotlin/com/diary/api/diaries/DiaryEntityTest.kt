package com.diary.api.diaries

import com.diary.api.emotions.DummyEmotion
import com.diary.api.users.DummyUser
import com.diary.api.weathers.DummyWeather
import com.diary.domain.entity.diaries.Diary
import com.diary.domain.entity.emotions.Emotion
import com.diary.domain.entity.users.User
import com.diary.domain.entity.weathers.Weather
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("일기 엔티티 단위 테스트")
class DiaryEntityTest {

    private val dummyDiary: Diary = DummyDiary.toEntity()

    @Test
    fun `일기 엔티티 작성자(회원) 엔티티 매핑`() {
        // given
        val dummyUser: User = DummyUser.toEntity()

        // when
        dummyDiary.set(writer = dummyUser)

        // then
        assertThat(dummyDiary.writer).isEqualTo(dummyUser)
    }

    @Test
    fun `일기 엔티티 날씨 엔티티 매핑`() {
        // given
        val dummyWeather: Weather = DummyWeather.toEntity()

        // when
        dummyDiary.set(weather = dummyWeather)

        // then
        assertThat(dummyDiary.weather).isEqualTo(dummyWeather)
    }

    @Test
    fun `일기 엔티티 감정 엔티티 매핑`() {
        // given
        val dummyEmotion: Emotion = DummyEmotion.toEntity()

        // when
        dummyDiary.set(emotion = dummyEmotion)

        // then
        assertThat(dummyDiary.emotion).isEqualTo(dummyEmotion)
    }

    @Test
    fun `일기 엔티티 삭제`() {
        // given

        // when
        dummyDiary.delete()

        // then
        assertThat(dummyDiary.deleted).isTrue()
    }
}
