package com.scrimmers.api.service.board

import com.scrimmers.api.dto.board.BoardResponseDto
import com.scrimmers.api.dto.board.CreateBoardRequestDto
import com.scrimmers.api.dto.board.UpdateBoardRequestDto
import com.scrimmers.api.dto.common.PaginationResponseDto
import com.scrimmers.api.service.board.converter.BoardConverter
import com.scrimmers.api.service.board.updater.BoardUpdater
import com.scrimmers.api.service.board.validator.BoardValidator
import com.scrimmers.domain.dto.common.Pagination
import com.scrimmers.domain.entity.borad.Board
import com.scrimmers.domain.entity.borad.BoardFinder
import com.scrimmers.domain.entity.borad.BoardOrderType
import com.scrimmers.domain.entity.borad.BoardQueryFilter
import com.scrimmers.domain.entity.borad.BoardRepository
import com.scrimmers.domain.entity.borad.category.BoardCategoryFinder
import com.scrimmers.domain.entity.user.UserFinder
import com.scrimmers.domain.exception.ErrorCode
import com.scrimmers.domain.exception.PolicyException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.Throws

@Service
class BoardService(
    private val repository: BoardRepository,
    private val finder: BoardFinder,
    private val userFinder: UserFinder,
    private val boardCategoryFinder: BoardCategoryFinder,
    private val validator: BoardValidator,
    private val converter: BoardConverter,
    private val updater: BoardUpdater
) {

    @Transactional
    fun create(userId: String, request: CreateBoardRequestDto): String {
        validator.validate(request)
        val user = userFinder.findById(userId)
        val boardCategory = boardCategoryFinder.findById(request.boardCategoryId)
        val board = converter.convert(request).also {
            it.setBy(user)
            it.setBy(boardCategory)
        }
        return repository.save(board).id
    }

    @Transactional(readOnly = true)
    fun getBoards(
        queryFilter: BoardQueryFilter,
        pagination: Pagination,
        orderTypes: List<BoardOrderType>?
    ): PaginationResponseDto {
        val boards = finder.search(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes
        )
        val totalCount = finder.searchCount(queryFilter)
        return PaginationResponseDto(
            skipCount = pagination.offset,
            limitCount = pagination.limit,
            totalCount = totalCount,
            data = boards.map { converter.convert(it) }
        )
    }

    @Transactional(readOnly = true)
    fun getBoard(boardId: String): BoardResponseDto {
        val board = finder.findById(boardId)
        return converter.convert(board)
    }

    @Transactional
    fun update(userId: String, boardId: String, request: UpdateBoardRequestDto): String {
        validator.validate(request)
        val board = finder.findById(boardId)
        validateWriter(
            userId = userId,
            board = board
        )
        updater.markAsUpdate(request = request, entity = board)
        return board.id
    }

    @Transactional
    fun delete(userId: String, boardId: String): Boolean {
        val board = finder.findById(boardId)
        validateWriter(
            userId = userId,
            board = board
        )
        board.delete()
        return true
    }

    @Throws(PolicyException::class)
    private fun validateWriter(userId: String, board: Board) {
        if (board.writer!!.id != userId) {
            throw PolicyException(
                errorCode = ErrorCode.NO_ACCESS_EXCEPT_FOR_WRITER,
                message = ErrorCode.NO_ACCESS_EXCEPT_FOR_WRITER.desc
            )
        }
    }
}
