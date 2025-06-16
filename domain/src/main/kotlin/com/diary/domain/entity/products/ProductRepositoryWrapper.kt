package com.diary.domain.entity.products

import com.diary.domain.dto.Pagination
import com.diary.domain.entity.products.QProduct.product
import com.diary.domain.exception.DataNotFoundException
import com.diary.domain.exception.ErrorCode
import com.diary.domain.type.ID
import com.querydsl.core.types.OrderSpecifier
import org.springframework.stereotype.Component
import kotlin.jvm.Throws

@Component
class ProductRepositoryWrapper(
    private val repository: ProductRepository,
    private val customRepository: ProductCustomRepository
) {

    fun save(product: Product): Product {
        return repository.save(product)
    }

    fun search(
        queryFilter: ProductQueryFilter,
        pagination: Pagination,
        orderTypes: List<ProductOrderType>
    ): List<Product> {
        return customRepository.search(
            booleanExpressions = queryFilter.toBooleanExpressions(),
            pagination = pagination,
            orderSpecifiers = orderTypes.toOrderSpecifiers()
        )
    }

    fun count(queryFilter: ProductQueryFilter): Long {
        return customRepository.count(booleanExpressions = queryFilter.toBooleanExpressions())
    }

    @Throws(DataNotFoundException::class)
    fun findById(id: ID): Product {
        return repository.findByIdAndDeletedFalse(id = id) ?: throw DataNotFoundException(
            errorCode = ErrorCode.PRODUCT_NOT_FOUND,
            message = ErrorCode.PRODUCT_NOT_FOUND.message
        )
    }

    private fun List<ProductOrderType>.toOrderSpecifiers(): Array<OrderSpecifier<*>> {
        return this.map {
            when (it) {
                ProductOrderType.ORDER_ASC -> product.order.asc()
                ProductOrderType.ORDER_DESC -> product.order.desc()
                ProductOrderType.CREATED_AT_ASC -> product.createdAt.asc()
                ProductOrderType.CREATED_AT_DESC -> product.createdAt.desc()
            }
        }.toTypedArray()
    }
}
