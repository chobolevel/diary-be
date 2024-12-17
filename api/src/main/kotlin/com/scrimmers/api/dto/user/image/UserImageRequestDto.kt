package com.scrimmers.api.dto.user.image

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.scrimmers.domain.entity.user.image.UserImageType
import com.scrimmers.domain.entity.user.image.UserImageUpdateMask
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CreateUserImageRequestDto(
    @field:NotNull(message = "이미지 타입은 필수 값입니다.")
    val type: UserImageType,
    @field:NotEmpty(message = "이미지 파일명(확장자 포함)은 필수 값입니다.")
    val name: String,
    @field:NotEmpty(message = "이미지 경로는 필수 값입니다.")
    val url: String
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UpdateUserImageRequestDto(
    val type: UserImageType?,
    val name: String?,
    val url: String?,
    @field:Size(min = 1, message = "update_mask는 필수 값입니다.")
    val updateMask: List<UserImageUpdateMask>
)
