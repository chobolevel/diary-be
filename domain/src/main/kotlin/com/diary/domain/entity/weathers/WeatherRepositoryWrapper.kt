package com.diary.domain.entity.weathers

import com.diary.domain.dto.Pagination
import com.diary.domain.entity.weathers.QWeather.weather
import com.diary.domain.exception.DataNotFoundException
import com.diary.domain.exception.ErrorCode
import com.diary.domain.type.ID
import com.querydsl.core.types.OrderSpecifier
import org.springframework.stereotype.Component
import kotlin.jvm.Throws

@Component
class WeatherRepositoryWrapper(
    private val repository: WeatherRepository,
    private val customRepository: WeatherCustomRepository
) {

    fun save(weather: Weather): Weather {
        return repository.save(weather)
    }

    fun search(
        queryFilter: WeatherQueryFilter,
        pagination: Pagination,
        orderTypes: List<WeatherOrderType>
    ): List<Weather> {
        return customRepository.search(
            booleanExpressions = queryFilter.toBooleanExpressions(),
            pagination = pagination,
            orderSpecifiers = orderTypes.toOrderSpecifiers()
        )
    }

    fun count(queryFilter: WeatherQueryFilter): Long {
        return customRepository.count(
            booleanExpressions = queryFilter.toBooleanExpressions(),
        )
    }

    @Throws(DataNotFoundException::class)
    fun findById(id: ID): Weather {
        return repository.findByIdAndDeletedFalse(id = id) ?: throw DataNotFoundException(
            errorCode = ErrorCode.WEATHER_NOT_FOUND,
            message = ErrorCode.WEATHER_NOT_FOUND.message
        )
    }

    private fun List<WeatherOrderType>.toOrderSpecifiers(): Array<OrderSpecifier<*>> {
        return this.map {
            when (it) {
                WeatherOrderType.CREATED_AT_ASC -> weather.createdAt.asc()
                WeatherOrderType.CREATED_AT_DESC -> weather.createdAt.desc()
            }
        }.toTypedArray()
    }
}
