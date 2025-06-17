package com.diary.api.service.products.converter

import com.diary.api.dto.products.options.CreateProductOptionRequestDto
import com.diary.domain.entity.products.options.ProductOption
import io.hypersistence.tsid.TSID
import org.springframework.stereotype.Component

@Component
class ProductOptionConverter {

    fun convert(request: CreateProductOptionRequestDto): ProductOption {
        return ProductOption(
            id = TSID.fast().toString(),
            name = request.name,
            order = request.order
        )
    }
}
