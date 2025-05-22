package com.diary.api.weathers

import com.diary.api.dto.weathers.CreateWeatherRequestDto
import com.diary.api.dto.weathers.UpdateWeatherRequestDto
import com.diary.api.dto.weathers.WeatherResponseDto
import com.diary.domain.entity.weathers.Weather
import com.diary.domain.entity.weathers.WeatherUpdateMask
import com.diary.domain.type.ID

object DummyWeather {
    private val id: ID = "0KH4WDSJA2CHB"
    private val name: String = "맑음"
    private val icon: String = "☀\uFE0F"
    private val order: Int = 0
    private val createdAt: Long = 0L
    private val updatedAt: Long = 0L

    private val weather: Weather by lazy {
        Weather(
            id = id,
            name = name,
            icon = icon,
            order = order
        )
    }

    private val createRequest: CreateWeatherRequestDto by lazy {
        CreateWeatherRequestDto(
            name = name,
            icon = icon,
            order = order
        )
    }
    private val updateRequest: UpdateWeatherRequestDto by lazy {
        UpdateWeatherRequestDto(
            name = "약간 맑음",
            icon = null,
            order = null,
            updateMask = listOf(WeatherUpdateMask.NAME)
        )
    }
    private val response: WeatherResponseDto by lazy {
        WeatherResponseDto(
            id = id,
            name = name,
            icon = icon,
            order = order,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    fun toEntity(): Weather {
        return weather
    }
    fun toCreateRequestDto(): CreateWeatherRequestDto {
        return createRequest
    }
    fun toUpdateRequestDto(): UpdateWeatherRequestDto {
        return updateRequest
    }
    fun toResponseDto(): WeatherResponseDto {
        return response
    }
}
