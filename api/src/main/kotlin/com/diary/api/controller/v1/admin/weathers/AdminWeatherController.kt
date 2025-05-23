package com.diary.api.controller.v1.admin.weathers

import com.diary.api.annotation.HasAuthorityAdmin
import com.diary.api.dto.common.ResultResponseDto
import com.diary.api.dto.weathers.CreateWeatherRequestDto
import com.diary.api.dto.weathers.UpdateWeatherRequestDto
import com.diary.api.service.weathers.WeatherService
import com.diary.domain.type.ID
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Weather(관리자 날씨)", description = "관리자 날씨 관리 API")
@RestController
@RequestMapping("/api/v1/admin")
class AdminWeatherController(
    private val service: WeatherService
) {

    @Operation(summary = "날씨 등록 API")
    @HasAuthorityAdmin
    @PostMapping("/weathers")
    fun create(
        @Valid @RequestBody
        request: CreateWeatherRequestDto
    ): ResponseEntity<ResultResponseDto> {
        val result: ID = service.create(request = request)
        return ResponseEntity.status(HttpStatus.CREATED).body(ResultResponseDto(result))
    }

    @Operation(summary = "날씨 정보 수정 API")
    @HasAuthorityAdmin
    @PutMapping("/weathers/{id}")
    fun update(
        @PathVariable("id") weatherId: ID,
        @Valid @RequestBody
        request: UpdateWeatherRequestDto
    ): ResponseEntity<ResultResponseDto> {
        val result: ID = service.update(
            request = request,
            weatherId = weatherId
        )
        return ResponseEntity.ok(ResultResponseDto(result))
    }

    @Operation(summary = "날씨 정보 삭제 API")
    @HasAuthorityAdmin
    @DeleteMapping("/weathers/{id}")
    fun delete(@PathVariable("id") weatherId: ID): ResponseEntity<ResultResponseDto> {
        val result: Boolean = service.delete(weatherId = weatherId)
        return ResponseEntity.ok(ResultResponseDto(result))
    }
}
