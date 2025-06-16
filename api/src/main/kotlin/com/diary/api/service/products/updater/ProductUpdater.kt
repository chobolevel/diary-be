package com.diary.api.service.products.updater

import com.diary.api.dto.products.UpdateProductRequestDto
import com.diary.domain.entity.products.Product
import com.diary.domain.entity.products.ProductUpdateMask
import com.diary.domain.entity.products.categories.ProductCategory
import com.diary.domain.entity.products.categories.ProductCategoryRepositoryWrapper
import org.springframework.stereotype.Component

@Component
class ProductUpdater(
    private val productCategoryRepositoryWrapper: ProductCategoryRepositoryWrapper
) {

    fun markAsUpdate(
        request: UpdateProductRequestDto,
        entity: Product
    ): Product {
        request.updateMask.forEach {
            when (it) {
                ProductUpdateMask.CATEGORY -> {
                    val productCategory: ProductCategory = productCategoryRepositoryWrapper.findById(id = request.productCategoryId!!)
                    entity.set(productCategory = productCategory)
                }
                ProductUpdateMask.NAME -> entity.name = request.name!!
                ProductUpdateMask.STATUS -> entity.status = request.status!!
                ProductUpdateMask.ORDER -> entity.order = request.order!!
            }
        }
        return entity
    }
}
