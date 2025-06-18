package com.diary.api.service.products.validator

import com.diary.api.dto.products.options.values.CreateProductOptionValueRequestDto
import com.diary.api.dto.products.options.values.UpdateProductOptionValueRequestDto
import com.diary.api.util.validateIsNull
import com.diary.api.util.validateIsSmallerThan
import com.diary.domain.entity.products.options.values.ProductOptionValueUpdateMask
import org.springframework.stereotype.Component

@Component
class ProductOptionValueValidator {

    fun validate(request: CreateProductOptionValueRequestDto) {
        request.order.validateIsSmallerThan(
            compareTo = 1,
            parameterName = "order"
        )
    }

    fun validate(request: UpdateProductOptionValueRequestDto) {
        request.updateMask.forEach {
            when (it) {
                ProductOptionValueUpdateMask.NAME -> {
                    request.name.validateIsNull(parameterName = "name")
                }
                ProductOptionValueUpdateMask.ORDER -> {
                    request.order.validateIsSmallerThan(
                        compareTo = 1,
                        parameterName = "order"
                    )
                }
            }
        }
    }
}
