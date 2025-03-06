package com.daangn.api.service.categories

import com.daangn.api.dto.categories.CategoryResponseDto
import com.daangn.api.dto.categories.CreateCategoryRequestDto
import com.daangn.api.dto.categories.UpdateCategoryRequestDto
import com.daangn.api.dto.common.PaginationResponseDto
import com.daangn.api.service.categories.converter.CategoryConverter
import com.daangn.api.service.categories.updater.CategoryUpdater
import com.daangn.api.service.categories.validator.CategoryValidator
import com.daangn.domain.dto.Pagination
import com.daangn.domain.entity.categories.CategoryOrderType
import com.daangn.domain.entity.categories.CategoryQueryFilter
import com.daangn.domain.entity.categories.CategoryRepositoryWrapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CategoryService(
    private val repositoryWrapper: CategoryRepositoryWrapper,
    private val converter: CategoryConverter,
    private val updater: CategoryUpdater,
    private val validator: CategoryValidator
) {

    @Transactional
    fun create(request: CreateCategoryRequestDto): String {
        validator.validate(request)
        val category = converter.convert(request)
        return repositoryWrapper.save(category).id
    }

    fun getCategories(
        queryFilter: CategoryQueryFilter,
        pagination: Pagination,
        orderTypes: List<CategoryOrderType>
    ): PaginationResponseDto {
        val categories = repositoryWrapper.search(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes
        )
        val totalCount = repositoryWrapper.searchCount(
            queryFilter = queryFilter,
        )
        return PaginationResponseDto(
            page = pagination.page,
            size = pagination.size,
            data = converter.convert(categories),
            totalCount = totalCount
        )
    }

    fun getCategory(categoryId: String): CategoryResponseDto {
        val category = repositoryWrapper.findById(categoryId)
        return converter.convert(category)
    }

    @Transactional
    fun update(categoryId: String, request: UpdateCategoryRequestDto): String {
        validator.validate(request)
        val category = repositoryWrapper.findById(categoryId)
        updater.markAsUpdate(
            request = request,
            entity = category
        )
        return categoryId
    }

    @Transactional
    fun delete(categoryId: String): Boolean {
        val category = repositoryWrapper.findById(categoryId)
        category.delete()
        return true
    }
}
