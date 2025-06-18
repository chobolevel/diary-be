package com.diary.api.products.options

import com.diary.api.dto.products.options.CreateProductOptionRequestDto
import com.diary.api.dto.products.options.ProductOptionResponseDto
import com.diary.api.dto.products.options.UpdateProductOptionRequestDto
import com.diary.api.products.options.values.DummyProductOptionValue
import com.diary.domain.entity.products.options.ProductOption
import com.diary.domain.entity.products.options.ProductOptionUpdateMask
import com.diary.domain.type.ID

object DummyProductOption {
    private val id: ID = "0KH4WDSJA2CHB"
    private val name: String = "사이즈"
    private val order: Int = 1
    private val createdAt: Long = 0L
    private val updatedAt: Long = 0L

    private val dummyProductOption: ProductOption by lazy {
        ProductOption(
            id = id,
            name = name,
            order = order
        )
    }
    private val dummyProductOptionResponse: ProductOptionResponseDto by lazy {
        ProductOptionResponseDto(
            id = id,
            name = name,
            order = order,
            productOptionValues = listOf(DummyProductOptionValue.toResponseDto()),
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
    private val createRequest: CreateProductOptionRequestDto by lazy {
        CreateProductOptionRequestDto(
            name = name,
            order = order,
            values = listOf(DummyProductOptionValue.toCreateRequestDto())
        )
    }
    private val updateRequest: UpdateProductOptionRequestDto by lazy {
        UpdateProductOptionRequestDto(
            name = "색상",
            order = null,
            updateMask = listOf(ProductOptionUpdateMask.NAME)
        )
    }

    fun toEntity(): ProductOption {
        return dummyProductOption
    }
    fun toResponseDto(): ProductOptionResponseDto {
        return dummyProductOptionResponse
    }
    fun toCreateRequestDto(): CreateProductOptionRequestDto {
        return createRequest
    }
    fun toUpdateRequestDto(): UpdateProductOptionRequestDto {
        return updateRequest
    }
}
