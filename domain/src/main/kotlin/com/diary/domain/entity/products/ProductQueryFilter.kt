package com.diary.domain.entity.products

import com.diary.domain.entity.products.QProduct.product
import com.diary.domain.type.ID
import com.querydsl.core.types.dsl.BooleanExpression

data class ProductQueryFilter(
    private val productCategoryId: ID?,
    private val status: ProductStatus?
) {

    fun toBooleanExpressions(): Array<BooleanExpression> {
        return listOfNotNull(
            productCategoryId?.let { product.productCategory.id.eq(it) },
            status?.let { product.status.eq(it) },
            product.deleted.isFalse
        ).toTypedArray()
    }
}
