package com.scrimmers.domain.entity.borad

import com.querydsl.core.types.dsl.BooleanExpression
import com.scrimmers.domain.entity.borad.QBoard.board

class BoardQueryFilter(
    private val writerId: String?,
    private val boardCategoryId: String?,
    private val boardCategoryCode: String?,
    private val title: String?,
) {

    fun toBooleanExpressions(): Array<BooleanExpression> {
        return listOfNotNull(
            writerId?.let { board.writer.id.eq(it) },
            boardCategoryId?.let { board.boardCategory.id.eq(it) },
            boardCategoryCode?.let { board.boardCategory.code.eq(it) },
            title?.let { board.title.like(it) },
            board.deleted.isFalse
        ).toTypedArray()
    }
}
