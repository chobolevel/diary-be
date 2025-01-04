package com.scrimmers.domain.entity.borad.category

import com.querydsl.core.types.OrderSpecifier
import com.scrimmers.domain.dto.common.Pagination
import com.scrimmers.domain.entity.borad.category.QBoardCategory.boardCategory
import com.scrimmers.domain.exception.DataNotFoundException
import com.scrimmers.domain.exception.ErrorCode
import org.springframework.stereotype.Component
import kotlin.jvm.Throws

@Component
class BoardCategoryFinder(
    private val repository: BoardCategoryRepository,
    private val customRepository: BoardCategoryCustomRepository
) {

    @Throws(DataNotFoundException::class)
    fun findById(id: String): BoardCategory {
        return repository.findByIdAndDeletedFalse(id) ?: throw DataNotFoundException(
            errorCode = ErrorCode.BOARD_CATEGORY_IS_NOT_FOUND,
            message = ErrorCode.BOARD_CATEGORY_IS_NOT_FOUND.desc
        )
    }

    fun search(
        queryFilter: BoardCategoryQueryFilter,
        pagination: Pagination,
        orderTypes: List<BoardCategoryOrderType>?
    ): List<BoardCategory> {
        return customRepository.searchByPredicates(
            booleanExpressions = queryFilter.toBooleanExpressions(),
            pagination = pagination,
            orderSpecifiers = getOrderSpecifiers(orderTypes ?: emptyList())
        )
    }

    fun searchCount(queryFilter: BoardCategoryQueryFilter): Long {
        return customRepository.countByPredicates(
            booleanExpressions = queryFilter.toBooleanExpressions(),
        )
    }

    private fun getOrderSpecifiers(orderTypes: List<BoardCategoryOrderType>): Array<OrderSpecifier<*>> {
        return orderTypes.map {
            when (it) {
                BoardCategoryOrderType.CREATED_AT_ASC -> boardCategory.createdAt.asc()
                BoardCategoryOrderType.CREATED_AT_DESC -> boardCategory.createdAt.desc()
                BoardCategoryOrderType.ORDER_ASC -> boardCategory.order.asc()
                BoardCategoryOrderType.ORDER_DESC -> boardCategory.order.desc()
            }
        }.toTypedArray()
    }
}
