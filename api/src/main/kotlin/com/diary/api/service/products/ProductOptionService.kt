package com.diary.api.service.products

import com.diary.api.dto.products.options.CreateProductOptionRequestDto
import com.diary.api.dto.products.options.UpdateProductOptionRequestDto
import com.diary.api.service.products.converter.ProductOptionConverter
import com.diary.api.service.products.updater.ProductOptionUpdater
import com.diary.api.service.products.validator.ProductOptionValidator
import com.diary.domain.entity.products.Product
import com.diary.domain.entity.products.ProductRepositoryWrapper
import com.diary.domain.entity.products.options.ProductOption
import com.diary.domain.entity.products.options.ProductOptionRepositoryWrapper
import com.diary.domain.type.ID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductOptionService(
    private val repositoryWrapper: ProductOptionRepositoryWrapper,
    private val productRepositoryWrapper: ProductRepositoryWrapper,
    private val converter: ProductOptionConverter,
    private val updater: ProductOptionUpdater,
    private val validator: ProductOptionValidator
) {

    @Transactional
    fun create(
        productId: ID,
        request: CreateProductOptionRequestDto
    ): ID {
        validator.validate(request = request)
        val productOption: ProductOption = converter.convert(request = request).also { productOption ->
            // mapping product
            val product: Product = productRepositoryWrapper.findById(id = productId)
            productOption.set(product = product)
        }
        return repositoryWrapper.save(productOption = productOption).id
    }

    @Transactional
    fun update(
        productId: ID,
        productOptionId: ID,
        request: UpdateProductOptionRequestDto
    ): ID {
        validator.validate(request = request)
        val productOption: ProductOption = repositoryWrapper.findByIdAndProductId(
            id = productOptionId,
            productId = productId
        )
        updater.markAsUpdate(
            request = request,
            entity = productOption
        )
        return productOptionId
    }

    @Transactional
    fun delete(
        productId: ID,
        productOptionId: ID
    ): Boolean {
        val productOption: ProductOption = repositoryWrapper.findByIdAndProductId(
            id = productOptionId,
            productId = productId
        )
        productOption.delete()
        return true
    }
}
