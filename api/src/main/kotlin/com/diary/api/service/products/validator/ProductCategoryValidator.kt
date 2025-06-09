package com.diary.api.service.products.validator

import com.diary.api.dto.products.categories.CreateProductCategoryRequestDto
import com.diary.api.dto.products.categories.UpdateProductCategoryRequestDto
import com.diary.api.util.validateIsNull
import com.diary.api.util.validateIsSmallerThan
import com.diary.domain.entity.products.categories.ProductCategoryUpdateMask
import org.springframework.stereotype.Component

@Component
class ProductCategoryValidator {

    fun validate(request: CreateProductCategoryRequestDto) {
        request.order.validateIsSmallerThan(
            compareTo = 1,
            parameterName = "order"
        )
    }

    fun validate(request: UpdateProductCategoryRequestDto) {
        request.updateMask.forEach {
            when (it) {
                ProductCategoryUpdateMask.NAME -> {
                    request.name.validateIsNull(parameterName = "name")
                }
                ProductCategoryUpdateMask.ORDER -> {
                    request.order.validateIsSmallerThan(
                        compareTo = 1,
                        parameterName = "order"
                    )
                }
                else -> Unit
            }
        }
    }
}
