package com.diary.api.products.variants

import com.diary.domain.entity.products.variants.ProductVariant
import com.diary.domain.type.ID

object DummyProductVariant {
    private val id: ID = "0KH4WDSJA2CHB"
    private val stock: Int = 100
    private val createdAt: Long = 0L
    private val updatedAt: Long = 0L

    private val dummyProductVariant: ProductVariant by lazy {
        ProductVariant(
            id = id,
            stock = stock
        )
    }

    fun toEntity(): ProductVariant {
        return dummyProductVariant
    }
}
