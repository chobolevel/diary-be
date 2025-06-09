package com.diary.api.products.categories

import com.diary.api.dto.products.categories.CreateProductCategoryRequestDto
import com.diary.api.dto.products.categories.ProductCategoryResponseDto
import com.diary.api.dto.products.categories.UpdateProductCategoryRequestDto
import com.diary.domain.entity.products.categories.ProductCategory
import com.diary.domain.entity.products.categories.ProductCategoryUpdateMask
import com.diary.domain.type.ID

object DummyProductCategory {
    private val id: ID = "0KH4WDSJA2CHB"
    private val name: String = "조명"
    private val imageUrl: String = "https://chobolevel.s3.ap-northeast-2.amazonaws.com/image/2025/05/26/9604d334-4fa3-4b17-8dd0-205ca309ed18.jpeg"
    private val order: Int = 1
    private val createdAt: Long = 0L
    private val updatedAt: Long = 0L

    private val dummyProductCategory: ProductCategory by lazy {
        ProductCategory(
            id = id,
            name = name,
            imageUrl = imageUrl,
            order = order
        )
    }
    private val dummyParentProductCategory: ProductCategory by lazy {
        ProductCategory(
            id = "0KH4WDSJA2CH1",
            name = "장스탠드",
            imageUrl = "https://chobolevel.s3.ap-northeast-2.amazonaws.com/image/2025/05/26/9604d334-4fa3-4b17-8dd0-205ca309ed18.jpeg",
            order = 1
        )
    }
    private val dummyProductCategoryResponse: ProductCategoryResponseDto by lazy {
        ProductCategoryResponseDto(
            id = id,
            name = name,
            imageUrl = imageUrl,
            order = order,
            child = emptyList(),
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
    private val createRequest: CreateProductCategoryRequestDto by lazy {
        CreateProductCategoryRequestDto(
            parentId = "0KH4WDSJA2CH1",
            name = name,
            imageUrl = imageUrl,
            order = order
        )
    }
    private val updateRequest: UpdateProductCategoryRequestDto by lazy {
        UpdateProductCategoryRequestDto(
            parentId = null,
            name = "식물",
            imageUrl = null,
            order = null,
            updateMask = listOf(ProductCategoryUpdateMask.NAME)
        )
    }

    fun toEntity(): ProductCategory {
        return dummyProductCategory
    }
    fun toParentEntity(): ProductCategory {
        return dummyParentProductCategory
    }
    fun toResponseDto(): ProductCategoryResponseDto {
        return dummyProductCategoryResponse
    }
    fun toCreateRequestDto(): CreateProductCategoryRequestDto {
        return createRequest
    }
    fun toUpdateRequestDto(): UpdateProductCategoryRequestDto {
        return updateRequest
    }
}
