package com.diary.api.products

import com.diary.api.dto.products.CreateProductRequestDto
import com.diary.api.dto.products.ProductResponseDto
import com.diary.api.dto.products.UpdateProductRequestDto
import com.diary.api.products.categories.DummyProductCategory
import com.diary.domain.entity.products.Product
import com.diary.domain.entity.products.ProductStatus
import com.diary.domain.entity.products.ProductUpdateMask
import com.diary.domain.type.ID

object DummyProduct {
    private val id: ID = "0KH4WDSJA2CHB"
    private val name: String = "2025 T1 Uniform Jersey"
    private val status: ProductStatus = ProductStatus.ON_SALE
    private val order: Int = 1
    private val createdAt: Long = 0L
    private val updatedAt: Long = 0L

    private val dummyProduct: Product by lazy {
        Product(
            id = id,
            name = name,
            status = status,
            order = order
        )
    }
    private val dummyProductResponse: ProductResponseDto by lazy {
        ProductResponseDto(
            id = id,
            category = DummyProductCategory.toResponseDto(),
            name = name,
            status = status,
            statusLabel = status.desc,
            order = order,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }
    private val createRequest: CreateProductRequestDto by lazy {
        CreateProductRequestDto(
            productCategoryId = "0KH4WDSJA2CHB",
            name = name,
            order = order
        )
    }
    private val updateRequest: UpdateProductRequestDto by lazy {
        UpdateProductRequestDto(
            productCategoryId = null,
            name = null,
            status = ProductStatus.HIDING,
            order = null,
            updateMask = listOf(ProductUpdateMask.STATUS)
        )
    }

    fun toEntity(): Product {
        return dummyProduct
    }
    fun toResponseDto(): ProductResponseDto {
        return dummyProductResponse
    }
    fun toCreateRequestDto(): CreateProductRequestDto {
        return createRequest
    }
    fun toUpdateRequestDto(): UpdateProductRequestDto {
        return updateRequest
    }
}
