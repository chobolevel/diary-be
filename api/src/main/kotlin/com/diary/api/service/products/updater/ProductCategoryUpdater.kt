package com.diary.api.service.products.updater

import com.diary.api.dto.products.categories.UpdateProductCategoryRequestDto
import com.diary.domain.entity.products.categories.ProductCategory
import com.diary.domain.entity.products.categories.ProductCategoryRepositoryWrapper
import com.diary.domain.entity.products.categories.ProductCategoryUpdateMask
import org.springframework.stereotype.Component

@Component
class ProductCategoryUpdater(
    private val productCategoryRepositoryWrapper: ProductCategoryRepositoryWrapper
) {

    fun markAsUpdate(
        request: UpdateProductCategoryRequestDto,
        entity: ProductCategory
    ): ProductCategory {
        request.updateMask.forEach {
            when (it) {
                ProductCategoryUpdateMask.PARENT -> {
                    if (request.parentId == null) {
                        entity.set(parent = null)
                    } else {
                        val parent: ProductCategory =
                            productCategoryRepositoryWrapper.findById(id = request.parentId)
                        entity.set(parent)
                    }
                }
                ProductCategoryUpdateMask.NAME -> entity.name = request.name!!
                ProductCategoryUpdateMask.IMAGE_URL -> entity.imageUrl = request.imageUrl
                ProductCategoryUpdateMask.ORDER -> entity.order = request.order!!
            }
        }
        return entity
    }
}
