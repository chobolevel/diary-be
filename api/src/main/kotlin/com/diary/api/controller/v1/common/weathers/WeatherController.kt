package com.diary.api.controller.v1.common.weathers

import com.diary.api.dto.common.PaginationResponseDto
import com.diary.api.dto.common.ResultResponseDto
import com.diary.api.dto.weathers.WeatherResponseDto
import com.diary.api.service.weathers.WeatherService
import com.diary.domain.dto.Pagination
import com.diary.domain.entity.weathers.WeatherOrderType
import com.diary.domain.entity.weathers.WeatherQueryFilter
import com.diary.domain.type.ID
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Weather(날씨)", description = "날씨 관리 API")
@RestController
@RequestMapping("/api/v1")
class WeatherController(
    private val service: WeatherService
) {

    @Operation(summary = "날씨 목록 조회 API")
    @GetMapping("/weathers")
    fun getWeathers(
        @RequestParam(required = false) name: String?,
        @RequestParam(required = false) page: Long?,
        @RequestParam(required = false) size: Long?,
        @RequestParam(required = false) orderTypes: List<WeatherOrderType>?
    ): ResponseEntity<ResultResponseDto> {
        val queryFilter = WeatherQueryFilter(
            name = name,
        )
        val pagination = Pagination(
            page = page ?: 1,
            size = size ?: 10,
        )
        val result: PaginationResponseDto = service.getWeathers(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes ?: emptyList()
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }

    @Operation(summary = "날씨 단건 조회 API")
    @GetMapping("/weathers/{id}")
    fun getWeather(@PathVariable("id") weatherId: ID): ResponseEntity<ResultResponseDto> {
        val result: WeatherResponseDto = service.getWeather(weatherId = weatherId)
        return ResponseEntity.ok(ResultResponseDto(result))
    }
}
