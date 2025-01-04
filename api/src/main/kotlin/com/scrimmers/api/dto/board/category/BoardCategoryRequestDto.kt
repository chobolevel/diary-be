package com.scrimmers.api.dto.board.category

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.scrimmers.domain.entity.borad.category.BoardCategoryUpdateMask
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero
import jakarta.validation.constraints.Size

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CreateBoardCategoryRequestDto(
    @field:NotEmpty(message = "카테고리 코드는 필수 값입니다.")
    val code: String,
    @field:NotEmpty(message = "카테고리명은 필수 값입니다.")
    val name: String,
    @field:NotNull(message = "카테고리 순서는 필수 값입니다.")
    @field:PositiveOrZero(message = "카테고리 순서로 음수는 지정할 수 없습니다.")
    val order: Int,
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UpdateBoardCategoryRequestDto(
    val code: String?,
    val name: String?,
    val order: Int?,
    @field:Size(min = 1, message = "update_mask는 필수 값입니다.")
    val updateMask: List<BoardCategoryUpdateMask>
)
