package com.scrimmers.domain.entity.borad.category

import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import com.scrimmers.domain.dto.common.Pagination
import com.scrimmers.domain.entity.borad.category.QBoardCategory.boardCategory
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class BoardCategoryCustomRepository : QuerydslRepositorySupport(BoardCategory::class.java) {

    fun searchByPredicates(
        booleanExpressions: Array<BooleanExpression>,
        pagination: Pagination,
        orderSpecifiers: Array<OrderSpecifier<*>>
    ): List<BoardCategory> {
        return from(boardCategory)
            .where(*booleanExpressions)
            .orderBy(*orderSpecifiers)
            .offset(pagination.offset)
            .limit(pagination.limit)
            .fetch()
    }

    fun countByPredicates(booleanExpressions: Array<BooleanExpression>): Long {
        return from(boardCategory)
            .where(*booleanExpressions)
            .fetchCount()
    }
}
