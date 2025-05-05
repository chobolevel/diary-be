package com.diary.domain.entity.weathers

import com.diary.domain.dto.Pagination
import com.diary.domain.entity.weathers.QWeather.weather
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class WeatherCustomRepository : QuerydslRepositorySupport(Weather::class.java) {

    fun search(
        booleanExpressions: Array<BooleanExpression>,
        pagination: Pagination,
        orderSpecifiers: Array<OrderSpecifier<*>>
    ): List<Weather> {
        return from(weather)
            .where(*booleanExpressions)
            .orderBy(*orderSpecifiers)
            .offset(pagination.offset)
            .limit(pagination.limit)
            .fetch()
    }

    fun count(
        booleanExpressions: Array<BooleanExpression>,
    ): Long {
        return from(weather)
            .where(*booleanExpressions)
            .fetchCount()
    }
}
