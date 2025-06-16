package com.diary.domain.entity.products

import com.diary.domain.dto.Pagination
import com.diary.domain.entity.products.QProduct.product
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class ProductCustomRepository : QuerydslRepositorySupport(Product::class.java) {

    fun search(
        booleanExpressions: Array<BooleanExpression>,
        pagination: Pagination,
        orderSpecifiers: Array<OrderSpecifier<*>>
    ): List<Product> {
        return from(product)
            .where(*booleanExpressions)
            .orderBy(*orderSpecifiers)
            .offset(pagination.offset)
            .limit(pagination.limit)
            .fetch()
    }

    fun count(booleanExpressions: Array<BooleanExpression>): Long {
        return from(product)
            .where(*booleanExpressions)
            .fetchCount()
    }
}
