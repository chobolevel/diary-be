package com.diary.api.products.options.values

import com.diary.api.dto.products.options.values.CreateProductOptionValueRequestDto
import com.diary.api.dto.products.options.values.ProductOptionValueResponseDto
import com.diary.api.dto.products.options.values.UpdateProductOptionValueRequestDto
import com.diary.domain.entity.products.options.values.ProductOptionValue
import com.diary.domain.entity.products.options.values.ProductOptionValueUpdateMask
import com.diary.domain.type.ID

object DummyProductOptionValue {
    private val id: ID = "0KH4WDSJA2CHB"
    private val name: String = "L"
    private val order: Int = 1
    private val createdAt: Long = 0L
    private val updatedAt: Long = 0L

    private val dummyProductOptionValue: ProductOptionValue by lazy {
        ProductOptionValue(
            id = id,
            name = name,
            order = order
        )
    }
    private val dummyProductOptionValueResponse: ProductOptionValueResponseDto by lazy {
        ProductOptionValueResponseDto(
            id = id,
            name = name,
            order = order,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }
    private val createRequest: CreateProductOptionValueRequestDto by lazy {
        CreateProductOptionValueRequestDto(
            name = name,
            order = order
        )
    }
    private val updateRequest: UpdateProductOptionValueRequestDto by lazy {
        UpdateProductOptionValueRequestDto(
            name = "M",
            order = null,
            updateMask = listOf(ProductOptionValueUpdateMask.NAME)
        )
    }

    fun toEntity(): ProductOptionValue {
        return dummyProductOptionValue
    }
    fun toResponseDto(): ProductOptionValueResponseDto {
        return dummyProductOptionValueResponse
    }
    fun toCreateRequestDto(): CreateProductOptionValueRequestDto {
        return createRequest
    }
    fun toUpdateRequestDto(): UpdateProductOptionValueRequestDto {
        return updateRequest
    }
}
