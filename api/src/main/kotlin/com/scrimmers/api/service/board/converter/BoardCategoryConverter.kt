package com.scrimmers.api.service.board.converter

import com.scrimmers.api.dto.board.category.BoardCategoryResponseDto
import com.scrimmers.api.dto.board.category.CreateBoardCategoryRequestDto
import com.scrimmers.domain.entity.borad.category.BoardCategory
import io.hypersistence.tsid.TSID
import org.springframework.stereotype.Component

@Component
class BoardCategoryConverter {

    fun convert(request: CreateBoardCategoryRequestDto): BoardCategory {
        return BoardCategory(
            id = TSID.fast().toString(),
            code = request.code,
            name = request.name,
            order = request.order
        )
    }

    fun convert(entity: BoardCategory): BoardCategoryResponseDto {
        return BoardCategoryResponseDto(
            id = entity.id,
            code = entity.code,
            name = entity.name,
            order = entity.order,
            createdAt = entity.createdAt!!.toInstant().toEpochMilli(),
            updatedAt = entity.updatedAt!!.toInstant().toEpochMilli()
        )
    }
}
