package com.diary.api.service.products

import com.diary.api.dto.products.options.values.CreateProductOptionValueRequestDto
import com.diary.api.dto.products.options.values.UpdateProductOptionValueRequestDto
import com.diary.api.service.products.converter.ProductOptionValueConverter
import com.diary.api.service.products.updater.ProductOptionValueUpdater
import com.diary.domain.entity.products.options.ProductOption
import com.diary.domain.entity.products.options.ProductOptionRepositoryWrapper
import com.diary.domain.entity.products.options.values.ProductOptionValue
import com.diary.domain.entity.products.options.values.ProductOptionValueRepositoryWrapper
import com.diary.domain.type.ID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductOptionValueService(
    private val repositoryWrapper: ProductOptionValueRepositoryWrapper,
    private val productOptionRepositoryWrapper: ProductOptionRepositoryWrapper,
    private val converter: ProductOptionValueConverter,
    private val updater: ProductOptionValueUpdater
) {

    @Transactional
    fun create(
        productId: ID,
        productOptionId: ID,
        request: CreateProductOptionValueRequestDto
    ): ID {
        val productOptionValue: ProductOptionValue = converter.convert(request = request).also { productOptionValue ->
            // mapping product option
            val productOption: ProductOption = productOptionRepositoryWrapper.findByIdAndProductId(
                id = productOptionId,
                productId = productId
            )
            productOptionValue.set(productOption = productOption)
        }
        return repositoryWrapper.save(productOptionValue = productOptionValue).id
    }

    @Transactional
    fun update(
        productId: ID,
        productOptionId: ID,
        productOptionValueId: ID,
        request: UpdateProductOptionValueRequestDto
    ): ID {
        val productOptionValue: ProductOptionValue = repositoryWrapper.findByIdProductIdAndProductOptionId(
            id = productOptionValueId,
            productId = productId,
            productOptionId = productOptionId
        )
        updater.markAsUpdate(
            request = request,
            entity = productOptionValue
        )
        return productOptionValueId
    }

    @Transactional
    fun delete(
        productId: ID,
        productOptionId: ID,
        productOptionValueId: ID
    ): Boolean {
        val productOptionValue: ProductOptionValue = repositoryWrapper.findByIdProductIdAndProductOptionId(
            id = productOptionValueId,
            productId = productId,
            productOptionId = productOptionId
        )
        productOptionValue.delete()
        return true
    }
}
