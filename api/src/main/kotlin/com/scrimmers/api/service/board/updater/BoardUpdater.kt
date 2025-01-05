package com.scrimmers.api.service.board.updater

import com.scrimmers.api.dto.board.UpdateBoardRequestDto
import com.scrimmers.domain.entity.borad.Board
import com.scrimmers.domain.entity.borad.BoardUpdateMask
import com.scrimmers.domain.entity.borad.category.BoardCategoryFinder
import org.springframework.stereotype.Component

@Component
class BoardUpdater(
    private val boardCategoryFinder: BoardCategoryFinder
) {

    fun markAsUpdate(request: UpdateBoardRequestDto, entity: Board): Board {
        request.updateMask.forEach {
            when (it) {
                BoardUpdateMask.CATEGORY -> entity.setBy(boardCategoryFinder.findById(request.boardCategoryId!!))
                BoardUpdateMask.TITLE -> entity.title = request.title!!
                BoardUpdateMask.CONTENT -> entity.content = request.content!!
            }
        }
        return entity
    }
}
