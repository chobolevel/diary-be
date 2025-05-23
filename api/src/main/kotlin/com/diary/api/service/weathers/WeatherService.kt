package com.diary.api.service.weathers

import com.diary.api.dto.common.PaginationResponseDto
import com.diary.api.dto.weathers.CreateWeatherRequestDto
import com.diary.api.dto.weathers.UpdateWeatherRequestDto
import com.diary.api.dto.weathers.WeatherResponseDto
import com.diary.api.service.weathers.converter.WeatherConverter
import com.diary.api.service.weathers.updater.WeatherUpdater
import com.diary.api.service.weathers.validator.WeatherValidator
import com.diary.domain.dto.Pagination
import com.diary.domain.entity.weathers.Weather
import com.diary.domain.entity.weathers.WeatherOrderType
import com.diary.domain.entity.weathers.WeatherQueryFilter
import com.diary.domain.entity.weathers.WeatherRepositoryWrapper
import com.diary.domain.type.ID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WeatherService(
    private val repositoryWrapper: WeatherRepositoryWrapper,
    private val converter: WeatherConverter,
    private val updater: WeatherUpdater,
    private val validator: WeatherValidator
) {

    @Transactional
    fun create(request: CreateWeatherRequestDto): ID {
        validator.validate(request = request)
        val weather: Weather = converter.convert(request = request)
        return repositoryWrapper.save(weather = weather).id
    }

    fun getWeathers(
        queryFilter: WeatherQueryFilter,
        pagination: Pagination,
        orderTypes: List<WeatherOrderType>
    ): PaginationResponseDto {
        val weathers: List<Weather> = repositoryWrapper.search(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes
        )
        val totalCount: Long = repositoryWrapper.count(queryFilter = queryFilter)
        return PaginationResponseDto(
            page = pagination.page,
            size = pagination.size,
            data = converter.convert(entities = weathers),
            totalCount = totalCount
        )
    }

    fun getWeather(weatherId: ID): WeatherResponseDto {
        val weather: Weather = repositoryWrapper.findById(id = weatherId)
        return converter.convert(entity = weather)
    }

    @Transactional
    fun update(
        weatherId: ID,
        request: UpdateWeatherRequestDto
    ): ID {
        validator.validate(request = request)
        val weather: Weather = repositoryWrapper.findById(id = weatherId)
        updater.markAsUpdate(
            request = request,
            entity = weather
        )
        return weatherId
    }

    @Transactional
    fun delete(weatherId: ID): Boolean {
        val weather: Weather = repositoryWrapper.findById(id = weatherId)
        weather.delete()
        return true
    }
}
