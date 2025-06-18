package com.diary.api.service.products

import com.diary.api.dto.common.PaginationResponseDto
import com.diary.api.dto.products.CreateProductRequestDto
import com.diary.api.dto.products.ProductResponseDto
import com.diary.api.dto.products.UpdateProductRequestDto
import com.diary.api.service.products.converter.ProductConverter
import com.diary.api.service.products.converter.ProductOptionConverter
import com.diary.api.service.products.converter.ProductOptionValueConverter
import com.diary.api.service.products.updater.ProductUpdater
import com.diary.api.service.products.validator.ProductValidator
import com.diary.domain.dto.Pagination
import com.diary.domain.entity.products.Product
import com.diary.domain.entity.products.ProductOrderType
import com.diary.domain.entity.products.ProductQueryFilter
import com.diary.domain.entity.products.ProductRepositoryWrapper
import com.diary.domain.entity.products.categories.ProductCategory
import com.diary.domain.entity.products.categories.ProductCategoryRepositoryWrapper
import com.diary.domain.type.ID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    private val repositoryWrapper: ProductRepositoryWrapper,
    private val productCategoryRepositoryWrapper: ProductCategoryRepositoryWrapper,
    private val converter: ProductConverter,
    private val productOptionConverter: ProductOptionConverter,
    private val productOptionValueConverter: ProductOptionValueConverter,
    private val updater: ProductUpdater,
    private val validator: ProductValidator
) {

    @Transactional
    fun create(request: CreateProductRequestDto): ID {
        validator.validate(request = request)
        val product: Product = converter.convert(request = request).also { product ->
            // mapping product category
            val productCategory: ProductCategory = productCategoryRepositoryWrapper.findById(id = request.productCategoryId)
            product.set(productCategory = productCategory)

            // mapping product options
            request.options.forEach { createProductOptionRequest ->
                productOptionConverter.convert(request = createProductOptionRequest).also { productOption ->
                    productOption.set(product = product)
                    createProductOptionRequest.values.forEach { createProductOptionValueRequest ->
                        productOptionValueConverter.convert(request = createProductOptionValueRequest).also { productOptionValue ->
                            productOptionValue.set(productOption = productOption)
                        }
                    }
                }
            }
        }
        return repositoryWrapper.save(product = product).id
    }

    @Transactional(readOnly = true)
    fun getProducts(
        queryFilter: ProductQueryFilter,
        pagination: Pagination,
        orderTypes: List<ProductOrderType>
    ): PaginationResponseDto {
        val products: List<Product> = repositoryWrapper.search(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes
        )
        val totalCount: Long = repositoryWrapper.count(queryFilter = queryFilter)
        return PaginationResponseDto(
            page = pagination.page,
            size = pagination.size,
            data = converter.convert(entities = products),
            totalCount = totalCount,
        )
    }

    @Transactional(readOnly = true)
    fun getProduct(productId: ID): ProductResponseDto {
        val product: Product = repositoryWrapper.findById(id = productId)
        return converter.convert(entity = product)
    }

    @Transactional
    fun update(
        productId: ID,
        request: UpdateProductRequestDto
    ): ID {
        validator.validate(request = request)
        val product: Product = repositoryWrapper.findById(id = productId)
        updater.markAsUpdate(
            request = request,
            entity = product
        )
        return productId
    }

    @Transactional
    fun delete(productId: ID): Boolean {
        val product: Product = repositoryWrapper.findById(id = productId)
        product.delete()
        return true
    }
}
