package com.diary.api.service.products.updater

import com.diary.api.dto.products.options.UpdateProductOptionRequestDto
import com.diary.domain.entity.products.options.ProductOption
import com.diary.domain.entity.products.options.ProductOptionUpdateMask
import org.springframework.stereotype.Component

@Component
class ProductOptionUpdater {

    fun markAsUpdate(
        request: UpdateProductOptionRequestDto,
        entity: ProductOption
    ): ProductOption {
        request.updateMask.forEach {
            when (it) {
                ProductOptionUpdateMask.NAME -> entity.name = request.name!!
                ProductOptionUpdateMask.ORDER -> entity.order = request.order!!
            }
        }
        return entity
    }
}
