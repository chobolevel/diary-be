package com.diary.api.weathers

import com.diary.domain.entity.weathers.Weather
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

    fun toEntity(): Weather {
        return weather
    }
}
