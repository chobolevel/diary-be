package com.diary.api.service.weathers.updater

import com.diary.api.dto.weathers.UpdateWeatherRequestDto
import com.diary.domain.entity.weathers.Weather
import com.diary.domain.entity.weathers.WeatherUpdateMask
import org.springframework.stereotype.Component

@Component
class WeatherUpdater {

    fun markAsUpdate(
        request: UpdateWeatherRequestDto,
        entity: Weather
    ): Weather {
        request.updateMask.forEach {
            when (it) {
                WeatherUpdateMask.NAME -> entity.name = request.name!!
                WeatherUpdateMask.ICON -> entity.icon = request.icon!!
                WeatherUpdateMask.ORDER -> entity.order = request.order!!
            }
        }
        return entity
    }
}
