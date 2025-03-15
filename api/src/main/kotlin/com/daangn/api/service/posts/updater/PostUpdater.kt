package com.daangn.api.service.posts.updater

import com.daangn.api.dto.posts.UpdatePostRequestDto
import com.daangn.api.service.posts.converter.PostImageConverter
import com.daangn.domain.entity.categories.CategoryRepositoryWrapper
import com.daangn.domain.entity.posts.Post
import com.daangn.domain.entity.posts.PostUpdateMask
import com.daangn.domain.entity.posts.image.PostImageType
import org.springframework.stereotype.Component

@Component
class PostUpdater(
    private val categoryRepositoryWrapper: CategoryRepositoryWrapper,
    private val postImageConverter: PostImageConverter,
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
                PostUpdateMask.MAIN_IMAGES -> {
                    entity.deleteImages(type = PostImageType.MAIN)
                    request.mainImages!!.forEach { mainImageRequest ->
                        postImageConverter.convert(mainImageRequest).also { mainPostImage ->
                            mainPostImage.set(entity)
                        }
                    }
                }
            }
        }
        return entity
    }
}
