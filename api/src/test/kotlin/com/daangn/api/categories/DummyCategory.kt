package com.daangn.api.categories

import com.daangn.api.dto.categories.CategoryResponseDto
import com.daangn.api.dto.categories.CreateCategoryRequestDto
import com.daangn.api.dto.categories.UpdateCategoryRequestDto
import com.daangn.domain.entity.categories.Category
import com.daangn.domain.entity.categories.CategoryUpdateMask
import io.hypersistence.tsid.TSID

object DummyCategory {
    private val id: String = TSID.fast().toString()
    private val iconUrl: String = "https://chobolevel.s3.ap-northeast-2.amazonaws.com/image/2024/08/30/93c5e6d9-a261-4179-967c-c032e859194e.jpg"
    private val name: String = "고양이"
    private val order: Int = 1
    private val createdAt: Long = 0L
    private val updatedAt: Long = 0L

    fun toCreateRequestDto(): CreateCategoryRequestDto {
        return createRequest
    }
    fun toEntity(): Category {
        return category
    }
    fun toResponseDto(): CategoryResponseDto {
        return categoryResponse
    }
    fun toUpdateRequestDto(): UpdateCategoryRequestDto {
        return updateRequest
    }

    private val createRequest: CreateCategoryRequestDto by lazy {
        CreateCategoryRequestDto(
            iconUrl = iconUrl,
            name = name,
            order = order
        )
    }
    private val category: Category by lazy {
        Category(
            id = id,
            iconUrl = iconUrl,
            name = name,
            order = order
        )
    }
    private val categoryResponse: CategoryResponseDto by lazy {
        CategoryResponseDto(
            id = id,
            iconUrl = iconUrl,
            name = name,
            order = order,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }
    private val updateRequest: UpdateCategoryRequestDto by lazy {
        UpdateCategoryRequestDto(
            iconUrl = null,
            name = "변경",
            order = null,
            updateMask = listOf(
                CategoryUpdateMask.NAME
            )
        )
    }
}
