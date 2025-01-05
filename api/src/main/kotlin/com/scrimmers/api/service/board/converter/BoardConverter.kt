package com.scrimmers.api.service.board.converter

import com.scrimmers.api.dto.board.BoardResponseDto
import com.scrimmers.api.dto.board.CreateBoardRequestDto
import com.scrimmers.api.service.user.converter.UserConverter
import com.scrimmers.domain.entity.borad.Board
import io.hypersistence.tsid.TSID
import org.springframework.stereotype.Component

@Component
class BoardConverter(
    private val userConverter: UserConverter,
    private val boardCategoryConverter: BoardCategoryConverter
) {

    fun convert(request: CreateBoardRequestDto): Board {
        return Board(
            id = TSID.fast().toString(),
            title = request.title,
            content = request.content
        )
    }

    fun convert(entity: Board): BoardResponseDto {
        return BoardResponseDto(
            id = entity.id,
            writer = userConverter.convert(entity.writer!!),
            category = boardCategoryConverter.convert(entity.boardCategory!!),
            title = entity.title,
            content = entity.content,
            createdAt = entity.createdAt!!.toInstant().toEpochMilli(),
            updatedAt = entity.updatedAt!!.toInstant().toEpochMilli()
        )
    }
}
