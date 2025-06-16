package com.diary.api.service.products.validator

import com.diary.api.dto.products.CreateProductRequestDto
import com.diary.api.dto.products.UpdateProductRequestDto
import com.diary.api.util.validateIsNull
import com.diary.api.util.validateIsSmallerThan
import com.diary.domain.entity.products.ProductUpdateMask
import org.springframework.stereotype.Component

@Component
class ProductValidator {

    fun validate(request: CreateProductRequestDto) {
        request.order.validateIsSmallerThan(
            compareTo = 1,
            parameterName = "order"
        )
    }

    fun validate(request: UpdateProductRequestDto) {
        request.updateMask.forEach {
            when (it) {
                ProductUpdateMask.CATEGORY -> {
                    request.productCategoryId.validateIsNull(parameterName = "product_category_id")
                }
                ProductUpdateMask.NAME -> {
                    request.name.validateIsNull(parameterName = "name")
                }
                ProductUpdateMask.STATUS -> {
                    request.status.validateIsNull(parameterName = "status")
                }
                ProductUpdateMask.ORDER -> {
                    request.order.validateIsSmallerThan(
                        compareTo = 1,
                        parameterName = "order"
                    )
                }
            }
        }
    }
}
