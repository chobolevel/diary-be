package com.scrimmers.domain.entity.borad.category

import com.querydsl.core.types.dsl.BooleanExpression
import com.scrimmers.domain.entity.borad.category.QBoardCategory.boardCategory

data class BoardCategoryQueryFilter(
    val code: String?,
    val name: String?,
) {

    fun toBooleanExpressions(): Array<BooleanExpression> {
        return listOfNotNull(
            code?.let { boardCategory.code.eq(it) },
            name?.let { boardCategory.name.eq(it) },
            boardCategory.deleted.isFalse
        ).toTypedArray()
    }
}
