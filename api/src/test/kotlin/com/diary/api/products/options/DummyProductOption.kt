package com.diary.api.products.options

import com.diary.domain.entity.products.options.ProductOption
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

    fun toEntity(): ProductOption {
        return dummyProductOption
    }
}
