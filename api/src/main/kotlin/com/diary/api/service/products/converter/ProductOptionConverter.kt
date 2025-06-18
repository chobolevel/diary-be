package com.diary.api.service.products.converter

import com.diary.api.dto.products.options.CreateProductOptionRequestDto
import com.diary.api.dto.products.options.ProductOptionResponseDto
import com.diary.domain.entity.products.options.ProductOption
import io.hypersistence.tsid.TSID
import org.springframework.stereotype.Component

@Component
class ProductOptionConverter(
    private val productOptionValueConverter: ProductOptionValueConverter,
) {

    fun convert(request: CreateProductOptionRequestDto): ProductOption {
        return ProductOption(
            id = TSID.fast().toString(),
            name = request.name,
            order = request.order
        )
    }

    fun convert(entity: ProductOption): ProductOptionResponseDto {
        return ProductOptionResponseDto(
            id = entity.id,
            name = entity.name,
            order = entity.order,
            productOptionValues = productOptionValueConverter.convert(entities = entity.productOptionValues),
            createdAt = entity.createdAt!!.toInstant().toEpochMilli(),
            updatedAt = entity.updatedAt!!.toInstant().toEpochMilli(),
        )
    }

    fun convert(entities: List<ProductOption>): List<ProductOptionResponseDto> {
        return entities.map { convert(it) }
    }
}
