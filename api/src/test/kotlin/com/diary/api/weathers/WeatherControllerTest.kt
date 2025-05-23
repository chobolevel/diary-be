package com.diary.api.weathers

import com.diary.api.controller.v1.common.weathers.WeatherController
import com.diary.api.dto.common.PaginationResponseDto
import com.diary.api.dto.common.ResultResponseDto
import com.diary.api.dto.weathers.WeatherResponseDto
import com.diary.api.service.weathers.WeatherService
import com.diary.domain.dto.Pagination
import com.diary.domain.entity.weathers.Weather
import com.diary.domain.entity.weathers.WeatherQueryFilter
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
@DisplayName("날씨 컨트롤러 단위 테스트")
class WeatherControllerTest {

    private val dummyWeather: Weather = DummyWeather.toEntity()
    private val dummyWeatherResponse: WeatherResponseDto = DummyWeather.toResponseDto()

    @Mock
    private lateinit var service: WeatherService

    @InjectMocks
    private lateinit var controller: WeatherController

    @Test
    fun `날씨 목록 조회`() {
        // given
        val queryFilter = WeatherQueryFilter(
            name = null
        )
        val pagination = Pagination(
            page = 1,
            size = 10
        )
        val dummyWeatherResponses: List<WeatherResponseDto> = listOf(dummyWeatherResponse)
        val paginationResponse = PaginationResponseDto(
            page = pagination.page,
            size = pagination.size,
            data = dummyWeatherResponses,
            totalCount = dummyWeatherResponses.size.toLong()
        )
        `when`(
            service.getWeathers(
                queryFilter = queryFilter,
                pagination = pagination,
                orderTypes = emptyList()
            )
        ).thenReturn(paginationResponse)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.getWeathers(
            name = null,
            page = null,
            size = null,
            orderTypes = null
        )

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(paginationResponse)
    }

    @Test
    fun `날씨 단건 조회`() {
        // given
        val dummyWeatherId: ID = dummyWeather.id
        `when`(service.getWeather(weatherId = dummyWeatherId)).thenReturn(dummyWeatherResponse)

        // when
        val result: ResponseEntity<ResultResponseDto> = controller.getWeather(weatherId = dummyWeatherId)

        // then
        assertThat(result).isExactlyInstanceOf(ResponseEntity::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body?.data).isEqualTo(dummyWeatherResponse)
    }
}
