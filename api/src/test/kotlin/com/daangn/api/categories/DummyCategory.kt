package com.daangn.api.categories

import com.daangn.domain.entity.categories.Category
import io.hypersistence.tsid.TSID

object DummyCategory {
    private val id: String = TSID.fast().toString()
    private val iconUrl: String = "https://chobolevel.s3.ap-northeast-2.amazonaws.com/image/2024/08/30/93c5e6d9-a261-4179-967c-c032e859194e.jpg"
    private val name: String = "고양이"
    private val order: Int = 1
    private val createdAt: Long = 0L
    private val updatedAt: Long = 0L

    fun toEntity(): Category {
        return category
    }

    private val category: Category by lazy {
        Category(
            id = id,
            iconUrl = iconUrl,
            name = name,
            order = order
        )
    }
}
