package com.diary.api.service.products.converter

import com.diary.api.dto.products.CreateProductRequestDto
import com.diary.api.dto.products.ProductResponseDto
import com.diary.domain.entity.products.Product
import com.diary.domain.entity.products.ProductStatus
import io.hypersistence.tsid.TSID
import org.springframework.stereotype.Component

@Component
class ProductConverter(
    private val productCategoryConverter: ProductCategoryConverter,
    private val productOptionConverter: ProductOptionConverter
) {

    fun convert(request: CreateProductRequestDto): Product {
        return Product(
            id = TSID.fast().toString(),
            name = request.name,
            status = ProductStatus.ON_SALE,
            order = request.order,
        )
    }

    fun convert(entity: Product): ProductResponseDto {
        return ProductResponseDto(
            id = entity.id,
            category = productCategoryConverter.convert(entity = entity.productCategory!!),
            name = entity.name,
            status = entity.status,
            statusLabel = entity.status.desc,
            order = entity.order,
            productOptions = productOptionConverter.convert(entities = entity.productOptions),
            createdAt = entity.createdAt!!.toInstant().toEpochMilli(),
            updatedAt = entity.updatedAt!!.toInstant().toEpochMilli(),
        )
    }

    fun convert(entities: List<Product>): List<ProductResponseDto> {
        return entities.map { convert(it) }
    }
}
