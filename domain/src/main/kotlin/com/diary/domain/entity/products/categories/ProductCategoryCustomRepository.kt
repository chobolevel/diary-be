package com.diary.domain.entity.products.categories

import com.diary.domain.dto.Pagination
import com.diary.domain.entity.products.categories.QProductCategory.productCategory
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class ProductCategoryCustomRepository : QuerydslRepositorySupport(ProductCategory::class.java) {

    fun search(
        booleanExpressions: Array<BooleanExpression>,
        pagination: Pagination,
        orderSpecifiers: Array<OrderSpecifier<*>>
    ): List<ProductCategory> {
        return from(productCategory)
            .where(*booleanExpressions)
            .orderBy(*orderSpecifiers)
            .offset(pagination.offset)
            .limit(pagination.limit)
            .fetch()
    }

    fun count(booleanExpressions: Array<BooleanExpression>): Long {
        return from(productCategory)
            .where(*booleanExpressions)
            .fetchCount()
    }
}
