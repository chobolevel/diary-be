package com.daangn.domain.entity.categories

import com.daangn.domain.dto.Pagination
import com.daangn.domain.entity.categories.QCategory.category
import com.daangn.domain.exception.DataNotFoundException
import com.daangn.domain.exception.ErrorCode
import com.querydsl.core.types.OrderSpecifier
import org.springframework.stereotype.Component
import kotlin.jvm.Throws

@Component
class CategoryRepositoryWrapper(
    private val repository: CategoryRepository,
    private val customRepository: CategoryCustomRepository
) {

    fun save(category: Category): Category {
        return repository.save(category)
    }

    fun search(
        queryFilter: CategoryQueryFilter,
        pagination: Pagination,
        orderTypes: List<CategoryOrderType>
    ): List<Category> {
        return customRepository.searchByPredicates(
            booleanExpressions = queryFilter.toBooleanExpressions(),
            pagination = pagination,
            orderSpecifiers = orderTypes.toOrderSpecifiers()
        )
    }

    fun searchCount(
        queryFilter: CategoryQueryFilter,
    ): Long {
        return customRepository.countByPredicates(
            booleanExpressions = queryFilter.toBooleanExpressions(),
        )
    }

    @Throws(DataNotFoundException::class)
    fun findById(id: String): Category {
        return repository.findByIdAndDeletedFalse(id) ?: throw DataNotFoundException(
            errorCode = ErrorCode.CATEGORY_NOT_FOUND,
            message = ErrorCode.CATEGORY_NOT_FOUND.message,
        )
    }

    private fun List<CategoryOrderType>.toOrderSpecifiers(): Array<OrderSpecifier<*>> {
        return this.map {
            when (it) {
                CategoryOrderType.ORDER_ASC -> category.order.asc()
                CategoryOrderType.ORDER_DESC -> category.order.desc()
                CategoryOrderType.CREATED_AT_ASC -> category.createdAt.asc()
                CategoryOrderType.CREATED_AT_DESC -> category.createdAt.desc()
            }
        }.toTypedArray()
    }
}
