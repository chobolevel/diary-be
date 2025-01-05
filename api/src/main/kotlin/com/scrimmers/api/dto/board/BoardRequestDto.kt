package com.scrimmers.api.dto.board

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.scrimmers.domain.entity.borad.BoardUpdateMask
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CreateBoardRequestDto(
    @field:NotEmpty(message = "카테고리가 정보는 필수 값입니다.")
    val boardCategoryId: String,
    @field:NotEmpty(message = "게시글 제목은 필수 값입니다.")
    val title: String,
    @field:NotEmpty(message = "게시글 내용은 필수 값입니다.")
    val content: String,
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class UpdateBoardRequestDto(
    val boardCategoryId: String?,
    val title: String?,
    val content: String?,
    @field:Size(min = 1, message = "update_mask는 필수 값입니다.")
    val updateMask: List<BoardUpdateMask>
)
