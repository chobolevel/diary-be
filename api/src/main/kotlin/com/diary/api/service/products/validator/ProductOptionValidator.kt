package com.diary.api.service.products.validator

import com.diary.api.dto.products.options.CreateProductOptionRequestDto
import com.diary.api.dto.products.options.UpdateProductOptionRequestDto
import com.diary.api.util.validateIsNull
import com.diary.api.util.validateIsSmallerThan
import com.diary.domain.entity.products.options.ProductOptionUpdateMask
import org.springframework.stereotype.Component

@Component
class ProductOptionValidator {

    fun validate(request: CreateProductOptionRequestDto) {
        request.order.validateIsSmallerThan(
            compareTo = 1,
            parameterName = "order"
        )
    }

    fun validate(request: UpdateProductOptionRequestDto) {
        request.updateMask.forEach {
            when (it) {
                ProductOptionUpdateMask.NAME -> {
                    request.name.validateIsNull(parameterName = "name")
                }
                ProductOptionUpdateMask.ORDER -> {
                    request.order.validateIsSmallerThan(
                        compareTo = 1,
                        parameterName = "order"
                    )
                }
            }
        }
    }
}
