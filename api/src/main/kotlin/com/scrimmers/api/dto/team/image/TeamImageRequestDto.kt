package com.scrimmers.api.dto.team.image

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.scrimmers.domain.entity.team.image.TeamImageType
import com.scrimmers.domain.entity.team.image.TeamImageUpdateMask
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CreateTeamImageRequestDto(
    @field:NotNull(message = "이미지 유형은 필수 값입니다.")
    val type: TeamImageType,
    @field:NotEmpty(message = "이미지 파일명은 필수 값입니다.")
    val name: String,
    @field:NotEmpty(message = "이미지 경로는 필수 값입니다.")
    val url: String
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UpdateTeamImageRequestDto(
    val type: TeamImageType?,
    val name: String?,
    val url: String?,
    @field:Size(min = 1, message = "update_mask는 필수 값입니다.")
    val updateMask: List<TeamImageUpdateMask>
)
