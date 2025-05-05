package com.diary.api.weathers

import com.diary.domain.entity.weathers.Weather
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("날씨 엔티티 단위 테스트")
class WeatherEntityTest {

    private val dummyWeather: Weather = DummyWeather.toEntity()

    @Test
    fun `날씨 삭제`() {
        // given

        // when
        dummyWeather.delete()

        // then
        Assertions.assertThat(dummyWeather.deleted).isTrue()
    }
}
