package com.scrimmers.api.service.board

import com.scrimmers.api.dto.board.category.BoardCategoryResponseDto
import com.scrimmers.api.dto.board.category.CreateBoardCategoryRequestDto
import com.scrimmers.api.dto.board.category.UpdateBoardCategoryRequestDto
import com.scrimmers.api.dto.common.PaginationResponseDto
import com.scrimmers.api.service.board.converter.BoardCategoryConverter
import com.scrimmers.api.service.board.updater.BoardCategoryUpdater
import com.scrimmers.api.service.board.validator.BoardCategoryValidator
import com.scrimmers.domain.dto.common.Pagination
import com.scrimmers.domain.entity.borad.category.BoardCategoryFinder
import com.scrimmers.domain.entity.borad.category.BoardCategoryOrderType
import com.scrimmers.domain.entity.borad.category.BoardCategoryQueryFilter
import com.scrimmers.domain.entity.borad.category.BoardCategoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardCategoryService(
    private val repository: BoardCategoryRepository,
    private val finder: BoardCategoryFinder,
    private val validator: BoardCategoryValidator,
    private val converter: BoardCategoryConverter,
    private val updater: BoardCategoryUpdater
) {

    @Transactional
    fun create(request: CreateBoardCategoryRequestDto): String {
        validator.validate(request)
        val boardCategory = converter.convert(request)
        return repository.save(boardCategory).id
    }

    fun getBoardCategories(
        queryFilter: BoardCategoryQueryFilter,
        pagination: Pagination,
        orderTypes: List<BoardCategoryOrderType>?
    ): PaginationResponseDto {
        val boardCategories = finder.search(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes,
        )
        val totalCount = finder.searchCount(queryFilter)
        return PaginationResponseDto(
            skipCount = pagination.offset,
            limitCount = pagination.limit,
            totalCount = totalCount,
            data = boardCategories.map { converter.convert(it) }
        )
    }

    fun getBoardCategory(id: String): BoardCategoryResponseDto {
        val boardCategory = finder.findById(id)
        return converter.convert(boardCategory)
    }

    @Transactional
    fun update(boardCategoryId: String, request: UpdateBoardCategoryRequestDto): String {
        validator.validate(request)
        val boardCategory = finder.findById(boardCategoryId)
        updater.markAsUpdate(
            request = request,
            entity = boardCategory,
        )
        return boardCategory.id
    }

    @Transactional
    fun delete(boardCategoryId: String): Boolean {
        val boardCategory = finder.findById(boardCategoryId)
        // TODO 해당 카테고리 게시글이 있을 경우 카테고리 삭제 불가능
        boardCategory.delete()
        return true
    }
}
