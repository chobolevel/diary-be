package com.scrimmers.api.dto.scrim.request

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.scrimmers.domain.entity.scrim.request.ScrimRequestUpdateMask
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CreateScrimRequestRequestDto(
    @field:NotEmpty(message = "요청 팀 정보는 필수 값입니다.")
    val fromTeamId: String,
    @field:NotEmpty(message = "요청 받는 팀 정보는 필수 값입니다.")
    val toTeamId: String,
    @field:NotEmpty(message = "요청 인사말은 필수 값입니다.")
    val comment: String
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UpdateScrimRequestRequestDto(
    val comment: String?,
    @field:Size(min = 1, message = "update_mask는 필수 값입니다.")
    val updateMask: List<ScrimRequestUpdateMask>
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class RejectScrimRequestRequestDto(
    @field:NotEmpty(message = "거절 사유는 필수 값입니다.")
    val rejectReason: String
)
