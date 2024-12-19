package com.scrimmers.api.dto.scrim

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.scrimmers.domain.entity.team.scrim.ScrimType
import com.scrimmers.domain.entity.team.scrim.ScrimUpdateMask
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CreateScrimRequestDto(
    @field:NotEmpty(message = "스크림 요청 정보는 필수 값입니다.")
    val scrimRequestId: String,
    @field:NotEmpty(message = "스크림 홈팀 정보는 필수 값입니다.")
    val homeTeamId: String,
    @field:NotEmpty(message = "스크림 원정팀 정보는 필수 값입니다.")
    val awayTeamId: String,
    @field:NotNull(message = "스크림 경기 유형은 필수 값입니다.(BO_1, BO_3, BO_5)")
    val type: ScrimType,
    @field:NotEmpty(message = "스크림명은 필수 값입니다.")
    val name: String,
    @field:NotNull(message = "스크림 일시는 필수 값입니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    val startedAt: LocalDateTime
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UpdateScrimRequestDto(
    val type: ScrimType?,
    val name: String?,
    val startedAt: LocalDateTime?,
    @field:Size(min = 1, message = "update_mask는 필수 값입니다.")
    val updateMask: List<ScrimUpdateMask>
)
