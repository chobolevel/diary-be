package com.daangn.api.service.categories.converter

import com.daangn.api.dto.categories.CategoryResponseDto
import com.daangn.api.dto.categories.CreateCategoryRequestDto
import com.daangn.domain.entity.categories.Category
import io.hypersistence.tsid.TSID
import org.springframework.stereotype.Component

@Component
class CategoryConverter {

    fun convert(request: CreateCategoryRequestDto): Category {
        return Category(
            id = TSID.fast().toString(),
            iconUrl = request.iconUrl,
            name = request.name,
            order = request.order
        )
    }

    fun convert(entity: Category): CategoryResponseDto {
        return CategoryResponseDto(
            id = entity.id,
            iconUrl = entity.iconUrl,
            name = entity.name,
            order = entity.order,
            createdAt = entity.createdAt!!.toInstant().toEpochMilli(),
            updatedAt = entity.updatedAt!!.toInstant().toEpochMilli()
        )
    }

    fun convert(entities: List<Category>): List<CategoryResponseDto> {
        return entities.map { convert(it) }
    }
}
