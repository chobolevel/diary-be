package com.diary.api.service.products

import com.diary.api.dto.common.PaginationResponseDto
import com.diary.api.dto.products.categories.CreateProductCategoryRequestDto
import com.diary.api.dto.products.categories.ProductCategoryResponseDto
import com.diary.api.dto.products.categories.UpdateProductCategoryRequestDto
import com.diary.api.service.products.converter.ProductCategoryConverter
import com.diary.api.service.products.updater.ProductCategoryUpdater
import com.diary.api.service.products.validator.ProductCategoryValidator
import com.diary.domain.dto.Pagination
import com.diary.domain.entity.products.categories.ProductCategory
import com.diary.domain.entity.products.categories.ProductCategoryOrderType
import com.diary.domain.entity.products.categories.ProductCategoryQueryFilter
import com.diary.domain.entity.products.categories.ProductCategoryRepositoryWrapper
import com.diary.domain.type.ID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductCategoryService(
    private val repositoryWrapper: ProductCategoryRepositoryWrapper,
    private val converter: ProductCategoryConverter,
    private val updater: ProductCategoryUpdater,
    private val validator: ProductCategoryValidator
) {

    @Transactional
    fun create(request: CreateProductCategoryRequestDto): ID {
        validator.validate(request = request)
        val productCategory: ProductCategory = converter.convert(request = request).also { productCategory ->
            // mapping parent
            request.parentId?.let { parentId ->
                val parent: ProductCategory = repositoryWrapper.findById(id = parentId)
                productCategory.set(parent = parent)
            }
        }
        return repositoryWrapper.save(productCategory = productCategory).id
    }

    @Transactional(readOnly = true)
    fun getProductCategories(
        queryFilter: ProductCategoryQueryFilter,
        pagination: Pagination,
        orderTypes: List<ProductCategoryOrderType>
    ): PaginationResponseDto {
        val productCategories: List<ProductCategory> = repositoryWrapper.search(
            queryFilter = queryFilter,
            pagination = pagination,
            orderTypes = orderTypes
        )
        val totalCount: Long = repositoryWrapper.count(queryFilter = queryFilter)
        return PaginationResponseDto(
            page = pagination.page,
            size = pagination.size,
            data = converter.convert(entities = productCategories),
            totalCount = totalCount
        )
    }

    @Transactional(readOnly = true)
    fun getProductCategory(productCategoryId: ID): ProductCategoryResponseDto {
        val productCategory: ProductCategory = repositoryWrapper.findById(id = productCategoryId)
        return converter.convert(entity = productCategory)
    }

    @Transactional
    fun update(
        productCategoryId: ID,
        request: UpdateProductCategoryRequestDto
    ): ID {
        validator.validate(request = request)
        val productCategory: ProductCategory = repositoryWrapper.findById(id = productCategoryId)
        updater.markAsUpdate(
            request = request,
            entity = productCategory
        )
        return productCategoryId
    }

    @Transactional
    fun delete(productCategoryId: ID): Boolean {
        val productCategory: ProductCategory = repositoryWrapper.findById(id = productCategoryId)
        productCategory.delete()
        return true
    }
}
