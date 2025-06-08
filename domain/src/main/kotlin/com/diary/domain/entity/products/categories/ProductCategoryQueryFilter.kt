package com.diary.domain.entity.products.categories

import com.diary.domain.entity.products.categories.QProductCategory.productCategory
import com.querydsl.core.types.dsl.BooleanExpression

data class ProductCategoryQueryFilter(
    private val name: String?
) {

    fun toBooleanExpressions(): Array<BooleanExpression> {
        return listOfNotNull(
            name?.let { productCategory.name.startsWith(it) },
            productCategory.deleted.isTrue
        ).toTypedArray()
    }
}
