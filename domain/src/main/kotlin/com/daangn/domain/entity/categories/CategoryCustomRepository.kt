package com.daangn.domain.entity.categories

import com.daangn.domain.dto.Pagination
import com.daangn.domain.entity.categories.QCategory.category
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class CategoryCustomRepository : QuerydslRepositorySupport(Category::class.java) {

    fun searchByPredicates(
        booleanExpressions: Array<BooleanExpression>,
        pagination: Pagination,
        orderSpecifiers: Array<OrderSpecifier<*>>
    ): List<Category> {
        return from(category)
            .where(*booleanExpressions)
            .orderBy(*orderSpecifiers)
            .offset(pagination.offset)
            .limit(pagination.limit)
            .fetch()
    }

    fun countByPredicates(
        booleanExpressions: Array<BooleanExpression>,
    ): Long {
        return from(category)
            .where(*booleanExpressions)
            .fetchCount()
    }
}
