package com.scrimmers.api.service.board.updater

import com.scrimmers.api.dto.board.category.UpdateBoardCategoryRequestDto
import com.scrimmers.domain.entity.borad.category.BoardCategory
import com.scrimmers.domain.entity.borad.category.BoardCategoryUpdateMask
import org.springframework.stereotype.Component

@Component
class BoardCategoryUpdater {

    fun markAsUpdate(request: UpdateBoardCategoryRequestDto, entity: BoardCategory): BoardCategory {
        request.updateMask.forEach {
            when (it) {
                BoardCategoryUpdateMask.CODE -> entity.code = request.code!!
                BoardCategoryUpdateMask.NAME -> entity.name = request.name!!
                BoardCategoryUpdateMask.ORDER -> entity.order = request.order!!
            }
        }
        return entity
    }
}
