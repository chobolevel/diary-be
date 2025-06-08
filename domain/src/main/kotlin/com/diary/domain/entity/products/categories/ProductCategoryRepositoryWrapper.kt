package com.diary.domain.entity.products.categories

import com.diary.domain.dto.Pagination
import com.diary.domain.entity.products.categories.QProductCategory.productCategory
import com.diary.domain.exception.DataNotFoundException
import com.diary.domain.exception.ErrorCode
import com.diary.domain.type.ID
import com.querydsl.core.types.OrderSpecifier
import org.springframework.stereotype.Component
import kotlin.jvm.Throws

@Component
class ProductCategoryRepositoryWrapper(
    private val repository: ProductCategoryRepository,
    private val customRepository: ProductCategoryCustomRepository
) {

    fun save(productCategory: ProductCategory): ProductCategory {
        return repository.save(productCategory)
    }

    fun search(
        queryFilter: ProductCategoryQueryFilter,
        pagination: Pagination,
        orderTypes: List<ProductCategoryOrderType>
    ): List<ProductCategory> {
        return customRepository.search(
            booleanExpressions = queryFilter.toBooleanExpressions(),
            pagination = pagination,
            orderSpecifiers = orderTypes.toOrderSpecifiers()
        )
    }

    fun count(queryFilter: ProductCategoryQueryFilter): Long {
        return customRepository.count(booleanExpressions = queryFilter.toBooleanExpressions())
    }

    @Throws(DataNotFoundException::class)
    fun findById(id: ID): ProductCategory {
        return repository.findByIdAndDeletedFalse(id = id) ?: throw DataNotFoundException(
            errorCode = ErrorCode.PRODUCT_CATEGORY_NOT_FOUND,
            message = ErrorCode.PRODUCT_CATEGORY_NOT_FOUND.message
        )
    }

    private fun List<ProductCategoryOrderType>.toOrderSpecifiers(): Array<OrderSpecifier<*>> {
        return this.map {
            when (it) {
                ProductCategoryOrderType.ORDER_ASC -> productCategory.order.asc()
                ProductCategoryOrderType.ORDER_DESC -> productCategory.order.desc()
                ProductCategoryOrderType.CREATED_AT_ASC -> productCategory.createdAt.asc()
                ProductCategoryOrderType.CREATED_AT_DESC -> productCategory.createdAt.desc()
            }
        }.toTypedArray()
    }
}
