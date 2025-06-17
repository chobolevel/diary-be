package com.diary.domain.entity.products.options

import com.diary.domain.exception.DataNotFoundException
import com.diary.domain.exception.ErrorCode
import com.diary.domain.type.ID
import org.springframework.stereotype.Component

@Component
class ProductOptionRepositoryWrapper(
    private val repository: ProductOptionRepository
) {

    fun save(productOption: ProductOption): ProductOption {
        return repository.save(productOption)
    }

    @Throws(DataNotFoundException::class)
    fun findByIdAndProductId(
        id: ID,
        productId: ID
    ): ProductOption {
        return repository.findByIdAndProductIdAndDeletedFalse(
            id = id,
            productId = productId
        ) ?: throw DataNotFoundException(
            errorCode = ErrorCode.PRODUCT_OPTION_NOT_FOUND,
            message = ErrorCode.PRODUCT_OPTION_NOT_FOUND.message
        )
    }
}
