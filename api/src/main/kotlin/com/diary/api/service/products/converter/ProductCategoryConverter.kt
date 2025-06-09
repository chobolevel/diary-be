package com.diary.api.service.products.converter

import com.diary.api.dto.products.categories.CreateProductCategoryRequestDto
import com.diary.api.dto.products.categories.ProductCategoryResponseDto
import com.diary.domain.entity.products.categories.ProductCategory
import io.hypersistence.tsid.TSID
import org.springframework.stereotype.Component

@Component
class ProductCategoryConverter {

    fun convert(request: CreateProductCategoryRequestDto): ProductCategory {
        return ProductCategory(
            id = TSID.fast().toString(),
            name = request.name,
            imageUrl = request.imageUrl,
            order = request.order,
        )
    }

    fun convert(entity: ProductCategory): ProductCategoryResponseDto {
        return ProductCategoryResponseDto(
            id = entity.id,
            name = entity.name,
            imageUrl = entity.imageUrl,
            order = entity.order,
            child = convert(entities = entity.child),
            createdAt = entity.createdAt!!.toInstant().toEpochMilli(),
            updatedAt = entity.updatedAt!!.toInstant().toEpochMilli()
        )
    }

    fun convert(entities: List<ProductCategory>): List<ProductCategoryResponseDto> {
        return entities.map { convert(it) }
    }
}
