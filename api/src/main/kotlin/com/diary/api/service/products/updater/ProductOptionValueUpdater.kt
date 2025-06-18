package com.diary.api.service.products.updater

import com.diary.api.dto.products.options.values.UpdateProductOptionValueRequestDto
import com.diary.domain.entity.products.options.values.ProductOptionValue
import com.diary.domain.entity.products.options.values.ProductOptionValueUpdateMask
import org.springframework.stereotype.Component

@Component
class ProductOptionValueUpdater {

    fun markAsUpdate(
        request: UpdateProductOptionValueRequestDto,
        entity: ProductOptionValue
    ): ProductOptionValue {
        request.updateMask.forEach {
            when (it) {
                ProductOptionValueUpdateMask.NAME -> entity.name = request.name!!
                ProductOptionValueUpdateMask.ORDER -> entity.order = request.order!!
            }
        }
        return entity
    }
}
