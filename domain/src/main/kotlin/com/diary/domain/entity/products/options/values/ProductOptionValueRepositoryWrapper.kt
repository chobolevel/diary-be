package com.diary.domain.entity.products.options.values

import com.diary.domain.exception.DataNotFoundException
import com.diary.domain.exception.ErrorCode
import com.diary.domain.type.ID
import org.springframework.stereotype.Repository
import kotlin.jvm.Throws

@Repository
class ProductOptionValueRepositoryWrapper(
    private val repository: ProductOptionValueRepository
) {

    fun save(productOptionValue: ProductOptionValue): ProductOptionValue {
        return repository.save(productOptionValue)
    }

    @Throws(DataNotFoundException::class)
    fun findByIdProductIdAndProductOptionId(
        id: ID,
        productId: ID,
        productOptionId: ID
    ): ProductOptionValue {
        return repository.findByIdAndProductIdAndProductOptionIdAndDeletedFalse(
            id = id,
            productId = productId,
            productOptionId = productOptionId
        ) ?: throw DataNotFoundException(
            errorCode = ErrorCode.PRODUCT_OPTION_VALUE_NOT_FOUND,
            message = ErrorCode.PRODUCT_OPTION_VALUE_NOT_FOUND.message
        )
    }
}
