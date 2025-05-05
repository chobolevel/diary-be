package com.diary.domain.entity.weathers

import com.diary.domain.entity.weathers.QWeather.weather
import com.querydsl.core.types.dsl.BooleanExpression

data class WeatherQueryFilter(
    private val name: String?
) {

    fun toBooleanExpressions(): Array<BooleanExpression> {
        return listOfNotNull(
            name?.let { weather.name.eq(it) },
            weather.deleted.isFalse
        ).toTypedArray()
    }
}
