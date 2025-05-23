package com.diary.api.weathers

import com.diary.api.dto.common.PaginationResponseDto
import com.diary.api.dto.weathers.CreateWeatherRequestDto
import com.diary.api.dto.weathers.UpdateWeatherRequestDto
import com.diary.api.dto.weathers.WeatherResponseDto
import com.diary.api.service.weathers.WeatherService
import com.diary.api.service.weathers.converter.WeatherConverter
import com.diary.api.service.weathers.updater.WeatherUpdater
import com.diary.api.service.weathers.validator.WeatherValidator
import com.diary.domain.dto.Pagination
import com.diary.domain.entity.weathers.Weather
import com.diary.domain.entity.weathers.WeatherOrderType
import com.diary.domain.entity.weathers.WeatherQueryFilter
import com.diary.domain.entity.weathers.WeatherRepositoryWrapper
import com.diary.domain.type.ID
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
@DisplayName("날씨 서비스 로직 단위 테스트")
class WeatherServiceTest {

    private val dummyWeather: Weather = DummyWeather.toEntity()
    private val dummyWeatherResponse: WeatherResponseDto = DummyWeather.toResponseDto()

    @Mock
    private lateinit var repositoryWrapper: WeatherRepositoryWrapper

    @Mock
    private lateinit var converter: WeatherConverter

    @Mock
    private lateinit var updater: WeatherUpdater

    @Mock
    private lateinit var validator: WeatherValidator

    @InjectMocks
    private lateinit var service: WeatherService

    @Test
    fun `날씨 등록`() {
        // given
        val request: CreateWeatherRequestDto = DummyWeather.toCreateRequestDto()
        `when`(converter.convert(request = request)).thenReturn(dummyWeather)
        `when`(repositoryWrapper.save(weather = dummyWeather)).thenReturn(dummyWeather)

        // when
        val result: ID = service.create(request = request)

        // then
        assertThat(result).isEqualTo(dummyWeather.id)
    }

    @Test
    fun `날씨 목록 조회`() {
        // given
        val queryFilter = WeatherQueryFilter(
            name = null
        )
        val pagination = Pagination(
            page = 1,
            size = 20
        )
        val orderTypes: List<WeatherOrderType> = emptyList()
        val dummyWeathers: List<Weather> = listOf(dummyWeather)
        val dummyWeatherResponses: List<WeatherResponseDto> = listOf(dummyWeatherResponse)
        `when`(
            repositoryWrapper.search(
                queryFilter = queryFilter,
                pagination = pagination,
                orderTypes = orderTypes
            )
        ).thenReturn(dummyWeathers)
        `when`(repositoryWrapper.count(queryFilter = queryFilter)).thenReturn(dummyWeathers.size.toLong())
        `when`(converter.convert(entities = dummyWeathers)).thenReturn(dummyWeatherResponses)

        // when
        val result: PaginationResponseDto = service.getWeathers(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes
        )

        // then
        assertThat(result.page).isEqualTo(pagination.page)
        assertThat(result.size).isEqualTo(pagination.size)
        assertThat(result.data).isEqualTo(dummyWeatherResponses)
        assertThat(result.totalCount).isEqualTo(dummyWeatherResponses.size.toLong())
    }

    @Test
    fun `날씨 단건 조회`() {
        // given
        val dummyWeatherId: ID = dummyWeather.id
        `when`(repositoryWrapper.findById(id = dummyWeatherId)).thenReturn(dummyWeather)
        `when`(converter.convert(entity = dummyWeather)).thenReturn(dummyWeatherResponse)

        // when
        val result: WeatherResponseDto = service.getWeather(weatherId = dummyWeatherId)

        // then
        assertThat(result).isEqualTo(dummyWeatherResponse)
    }

    @Test
    fun `날씨 정보 수정`() {
        // given
        val dummyWeatherId: ID = dummyWeather.id
        val request: UpdateWeatherRequestDto = DummyWeather.toUpdateRequestDto()
        `when`(repositoryWrapper.findById(id = dummyWeatherId)).thenReturn(dummyWeather)
        `when`(
            updater.markAsUpdate(
                request = request,
                entity = dummyWeather
            )
        ).thenReturn(dummyWeather)

        // when
        val result: ID = service.update(
            weatherId = dummyWeatherId,
            request = request
        )

        // then
        assertThat(result).isEqualTo(dummyWeatherId)
    }

    @Test
    fun `날씨 정보 삭제`() {
        // given
        val dummyWeatherId: ID = dummyWeather.id
        `when`(repositoryWrapper.findById(id = dummyWeatherId)).thenReturn(dummyWeather)

        // when
        val result: Boolean = service.delete(weatherId = dummyWeatherId)

        // then
        assertThat(result).isTrue()
    }
}
