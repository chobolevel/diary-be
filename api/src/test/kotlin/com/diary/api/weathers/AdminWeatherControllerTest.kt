package com.diary.api.weathers

import com.diary.api.controller.v1.admin.weathers.AdminWeatherController
import com.diary.api.dto.common.ResultResponseDto
import com.diary.api.dto.weathers.CreateWeatherRequestDto
import com.diary.api.dto.weathers.UpdateWeatherRequestDto
import com.diary.api.service.weathers.WeatherService
import com.diary.domain.entity.weathers.Weather
import com.diary.domain.type.ID
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@ExtendWith(MockitoExtension::class)
@DisplayName("관리자 날씨 컨트롤러 단위 테스트")
class AdminWeatherControllerTest {

    private val dummyWeather: Weather = DummyWeather.toEntity()

    @Mock
    private lateinit var service: WeatherService

    @InjectMocks
    private lateinit var controller: AdminWeatherController

    @Test
    fun `날씨 등록`() {
        // given
        val dummyWeatherId: ID = dummyWeather.id
        val request: CreateWeatherRequestDto = DummyWeather.toCreateRequestDto()
        `when`(service.create(request = request)).thenReturn(dummyWeatherId)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.create(request = request)

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.CREATED)
        assertThat(result.body?.data).isEqualTo(dummyWeatherId)
    }

    @Test
    fun `날씨 정보 수정`() {
        // given
        val dummyWeatherId: ID = dummyWeather.id
        val request: UpdateWeatherRequestDto = DummyWeather.toUpdateRequestDto()
        `when`(
            service.update(
                weatherId = dummyWeatherId,
                request = request
            )
        ).thenReturn(dummyWeatherId)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.update(
            weatherId = dummyWeatherId,
            request = request
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(dummyWeatherId)
    }

    @Test
    fun `날씨 정보 삭제`() {
        // given
        val dummyWeatherId: ID = dummyWeather.id
        `when`(service.delete(weatherId = dummyWeatherId)).thenReturn(true)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.delete(weatherId = dummyWeatherId)

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(true)
    }
}
