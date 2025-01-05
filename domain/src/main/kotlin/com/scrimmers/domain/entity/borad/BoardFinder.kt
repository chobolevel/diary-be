package com.scrimmers.domain.entity.borad

import com.querydsl.core.types.OrderSpecifier
import com.scrimmers.domain.dto.common.Pagination
import com.scrimmers.domain.entity.borad.QBoard.board
import com.scrimmers.domain.exception.DataNotFoundException
import com.scrimmers.domain.exception.ErrorCode
import org.springframework.stereotype.Component
import kotlin.jvm.Throws

@Component
class BoardFinder(
    private val repository: BoardRepository,
    private val customRepository: BoardCustomRepository
) {

    @Throws(DataNotFoundException::class)
    fun findById(id: String): Board {
        return repository.findByIdAndDeletedFalse(id) ?: throw DataNotFoundException(
            errorCode = ErrorCode.BOARD_IS_NOT_FOUND,
            message = ErrorCode.BOARD_IS_NOT_FOUND.desc
        )
    }

    fun findByBoardCategoryId(boardCategoryId: String): List<Board> {
        return repository.findByBoardCategoryIdAndDeletedFalse(boardCategoryId)
    }

    fun search(queryFilter: BoardQueryFilter, pagination: Pagination, orderTypes: List<BoardOrderType>?): List<Board> {
        return customRepository.searchByPredicates(
            booleanExpressions = queryFilter.toBooleanExpressions(),
            pagination = pagination,
            orderSpecifiers = getOrderSpecifiers(orderTypes ?: emptyList())
        )
    }

    fun searchCount(queryFilter: BoardQueryFilter): Long {
        return customRepository.countByPredicates(
            booleanExpressions = queryFilter.toBooleanExpressions(),
        )
    }

    private fun getOrderSpecifiers(orderTypes: List<BoardOrderType>): Array<OrderSpecifier<*>> {
        return orderTypes.map {
            when (it) {
                BoardOrderType.CREATED_AT_ASC -> board.createdAt.asc()
                BoardOrderType.CREATED_AT_DESC -> board.createdAt.desc()
            }
        }.toTypedArray()
    }
}
