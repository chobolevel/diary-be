package com.diary.api.service.weathers.converter

import com.diary.api.dto.weathers.CreateWeatherRequestDto
import com.diary.api.dto.weathers.WeatherResponseDto
import com.diary.domain.entity.weathers.Weather
import io.hypersistence.tsid.TSID
import org.springframework.stereotype.Component

@Component
class WeatherConverter {

    fun convert(request: CreateWeatherRequestDto): Weather {
        return Weather(
            id = TSID.fast().toString(),
            name = request.name,
            icon = request.icon,
            order = request.order ?: 0
        )
    }

    fun convert(entity: Weather): WeatherResponseDto {
        return WeatherResponseDto(
            id = entity.id,
            name = entity.name,
            icon = entity.icon,
            order = entity.order,
            createdAt = entity.createdAt!!.toInstant().toEpochMilli(),
            updatedAt = entity.updatedAt!!.toInstant().toEpochMilli()
        )
    }

    fun convert(entities: List<Weather>): List<WeatherResponseDto> {
        return entities.map { convert(it) }
    }
}
