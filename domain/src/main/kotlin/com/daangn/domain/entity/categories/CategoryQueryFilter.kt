package com.daangn.domain.entity.categories

import com.daangn.domain.entity.categories.QCategory.category
import com.querydsl.core.types.dsl.BooleanExpression

data class CategoryQueryFilter(
    private val name: String?
) {

    fun toBooleanExpressions(): Array<BooleanExpression> {
        return listOfNotNull(
            name?.let { category.name.startsWith(it) },
            category.deleted.isFalse
        ).toTypedArray()
    }
}
