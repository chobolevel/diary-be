package com.scrimmers.api.dto.user

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.scrimmers.domain.entity.user.UserLoginType
import com.scrimmers.domain.entity.user.UserUpdateMask
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CreateUserRequestDto(
    @field:NotEmpty(message = "이메일(아이디)은 필수 값입니다.")
    val email: String,
    val password: String?,
    val socialId: String?,
    @field:NotNull(message = "로그인 유형은 필수 값입니다.")
    val loginType: UserLoginType,
    @field:NotEmpty(message = "닉네임은 필수 값입니다.")
    val nickname: String,
    @field:NotEmpty(message = "전화번호는 필수 값입니다.")
    val phone: String,
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UpdateUserRequestDto(
    val nickname: String?,
    val phone: String?,
    @field:Size(min = 1, message = "update_mask는 필수 값입니다.")
    val updateMask: List<UserUpdateMask>
)
