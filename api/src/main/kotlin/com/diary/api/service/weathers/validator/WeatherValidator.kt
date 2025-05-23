package com.diary.api.service.weathers.validator

import com.diary.api.dto.weathers.CreateWeatherRequestDto
import com.diary.api.dto.weathers.UpdateWeatherRequestDto
import com.diary.api.util.validateIsNull
import com.diary.api.util.validateIsSmallerThan
import com.diary.domain.entity.weathers.WeatherUpdateMask
import org.springframework.stereotype.Component

@Component
class WeatherValidator {

    fun validate(request: CreateWeatherRequestDto) {
        request.order?.validateIsSmallerThan(
            compareTo = 0,
            parameterName = "order"
        )
    }

    fun validate(request: UpdateWeatherRequestDto) {
        request.updateMask.forEach {
            when (it) {
                WeatherUpdateMask.NAME -> {
                    request.name.validateIsNull(parameterName = "name")
                }
                WeatherUpdateMask.ICON -> {
                    request.icon.validateIsNull(parameterName = "icon")
                }
                WeatherUpdateMask.ORDER -> {
                    request.order.validateIsSmallerThan(
                        compareTo = 0,
                        parameterName = "order"
                    )
                }
            }
        }
    }
}
