package com.diary.api.service.products.converter

import com.diary.api.dto.products.options.values.CreateProductOptionValueRequestDto
import com.diary.api.dto.products.options.values.ProductOptionValueResponseDto
import com.diary.domain.entity.products.options.values.ProductOptionValue
import io.hypersistence.tsid.TSID
import org.springframework.stereotype.Component

@Component
class ProductOptionValueConverter {

    fun convert(request: CreateProductOptionValueRequestDto): ProductOptionValue {
        return ProductOptionValue(
            id = TSID.fast().toString(),
            name = request.name,
            order = request.order,
        )
    }

    fun convert(entity: ProductOptionValue): ProductOptionValueResponseDto {
        return ProductOptionValueResponseDto(
            id = entity.id,
            name = entity.name,
            order = entity.order,
            createdAt = entity.createdAt!!.toInstant().toEpochMilli(),
            updatedAt = entity.updatedAt!!.toInstant().toEpochMilli(),
        )
    }

    fun convert(entities: List<ProductOptionValue>): List<ProductOptionValueResponseDto> {
        return entities.map { convert(it) }
    }
}
