package com.daangn.api.service.posts.updater

import com.daangn.api.dto.posts.UpdatePostRequestDto
import com.daangn.domain.entity.categories.CategoryRepositoryWrapper
import com.daangn.domain.entity.posts.Post
import com.daangn.domain.entity.posts.PostUpdateMask
import org.springframework.stereotype.Component

@Component
class PostUpdater(
    private val categoryRepositoryWrapper: CategoryRepositoryWrapper
) {

    fun markAsUpdate(
        request: UpdatePostRequestDto,
        entity: Post
    ): Post {
        request.updateMask.forEach {
            when (it) {
                PostUpdateMask.CATEGORY -> {
                    val category = categoryRepositoryWrapper.findById(request.categoryId!!)
                    entity.set(category)
                }
                PostUpdateMask.TITLE -> entity.title = request.title!!
                PostUpdateMask.CONTENT -> entity.content = request.content!!
                PostUpdateMask.SALE_PRICE -> entity.salePrice = request.salePrice!!
                PostUpdateMask.FREE_SHARED -> entity.freeShared = request.freeShared!!
            }
        }
        return entity
    }
}
