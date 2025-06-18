package com.diary.api.products.options.values

import com.diary.domain.entity.products.options.values.ProductOptionValue
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

    fun toEntity(): ProductOptionValue {
        return dummyProductOptionValue
    }
}
