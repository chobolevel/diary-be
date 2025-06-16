package com.diary.api.products

import com.diary.domain.entity.products.Product
import com.diary.domain.entity.products.ProductStatus
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

    fun toEntity(): Product {
        return dummyProduct
    }
}
