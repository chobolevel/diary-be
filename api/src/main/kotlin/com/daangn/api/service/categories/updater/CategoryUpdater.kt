package com.daangn.api.service.categories.updater

import com.daangn.api.dto.categories.UpdateCategoryRequestDto
import com.daangn.domain.entity.categories.Category
import com.daangn.domain.entity.categories.CategoryUpdateMask
import org.springframework.stereotype.Component

@Component
class CategoryUpdater {

    fun markAsUpdate(
        request: UpdateCategoryRequestDto,
        entity: Category
    ): Category {
        request.updateMask.forEach {
            when (it) {
                CategoryUpdateMask.ICON_URL -> entity.iconUrl = request.iconUrl!!
                CategoryUpdateMask.NAME -> entity.name = request.name!!
                CategoryUpdateMask.ORDER -> entity.order = request.order!!
            }
        }
        return entity
    }
}
